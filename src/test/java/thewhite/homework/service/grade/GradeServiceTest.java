package thewhite.homework.service.grade;

import com.querydsl.core.types.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Grade;
import thewhite.homework.repository.GradeRepository;
import thewhite.homework.service.grade.argument.CreateGradeArgument;
import thewhite.homework.service.grade.argument.SearchGradeArgument;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class GradeServiceTest {

    private final GradeRepository gradeRepository = Mockito.mock(GradeRepository.class);

    private final GradeService gradeService = new GradeServiceImpl(gradeRepository);

    @Test
    void create() {
        //Arrange
        Grade grade = Mockito.mock(Grade.class);

        CreateGradeArgument argument = CreateGradeArgument.builder()
                                                          .rating(5)
                                                          .comment("com")
                                                          .build();

        Mockito.when(gradeRepository.save(any())).thenReturn(grade);

        //Act
        Grade actual = gradeService.create(argument);

        //Assert
        ArgumentCaptor<Grade> gradeArgumentCaptor = ArgumentCaptor.forClass(Grade.class);

        Mockito.verify(gradeRepository).save(gradeArgumentCaptor.capture());

        Grade expectedGrade = Grade.builder()
                                   .rating(5)
                                   .comment("com")
                                   .build();

        Assertions.assertThat(actual).isEqualTo(grade);
        Assertions.assertThat(gradeArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(expectedGrade);
    }

    @Test
    void delete() {
        //Arrange
        UUID id = UUID.fromString("83c6e9a8-18ec-4c0b-afbf-31fee3ec9fc6");

        //Act
        gradeService.delete(id);

        //Assert
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        Mockito.verify(gradeRepository).deleteById(uuidArgumentCaptor.capture());

        Assertions.assertThat(uuidArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(id);
    }

    @Test
    void page() {
        //Arrange
        Long id = 1L;
        SearchGradeArgument argument = SearchGradeArgument.builder()
                                                          .entryId(id)
                                                          .rating(5)
                                                          .build();

        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<Grade> expectedPage = new PageImpl<>(Collections.singletonList(new Grade()));
        Mockito.when(gradeRepository.findAll(any(Predicate.class), eq(pageable))).thenReturn(expectedPage);

        //Act
        Page<Grade> result = gradeService.page(argument, pageable);

        //Assert
        ArgumentCaptor<Predicate> predicateArgumentCaptor = ArgumentCaptor.forClass(Predicate.class);

        Mockito.verify(gradeRepository).findAll(predicateArgumentCaptor.capture(), eq(pageable));

        Assertions.assertThat(result)
                  .usingRecursiveComparison()
                  .isEqualTo(expectedPage);
        Assertions.assertThat(predicateArgumentCaptor.getValue().toString())
                  .isEqualTo("grade.rating = 5 && grade.entry.id = 1");
    }
}
