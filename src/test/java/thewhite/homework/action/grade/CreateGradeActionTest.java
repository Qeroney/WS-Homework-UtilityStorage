package thewhite.homework.action.grade;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thewhite.homework.model.Entry;
import thewhite.homework.model.Grade;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.grade.GradeService;
import thewhite.homework.service.grade.argument.CreateGradeArgument;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

class CreateGradeActionTest {

    private final EntryService entryService = Mockito.mock(EntryService.class);

    private final GradeService gradeService = Mockito.mock(GradeService.class);

    private final CreateGradeAction action = new CreateGradeAction(entryService, gradeService);

    @Test
    void create() {
        //Arrange
        Grade expectedGrade = Mockito.mock(Grade.class);
        Entry entry = Mockito.mock(Entry.class);
        Long entryId = 1L;

        CreateGradeActionArgument argument = CreateGradeActionArgument.builder()
                                                                      .rating(5)
                                                                      .comment("com")
                                                                      .entryId(entryId)
                                                                      .build();

        Mockito.when(entryService.getExisting(entryId)).thenReturn(entry);
        Mockito.when(gradeService.create(any())).thenReturn(expectedGrade);

        //Act
        Grade execute = action.execute(argument);

        //Assert
        ArgumentCaptor<CreateGradeArgument> gradeArgumentArgumentCaptor = ArgumentCaptor.forClass(CreateGradeArgument.class);

        Mockito.verify(gradeService).create(gradeArgumentArgumentCaptor.capture());

        CreateGradeArgument expectedArgument = CreateGradeArgument.builder()
                                                                  .rating(5)
                                                                  .comment("com")
                                                                  .entry(entry)
                                                                  .build();

        Assertions.assertThat(execute).isSameAs(expectedGrade);

        Assertions.assertThat(gradeArgumentArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(expectedArgument);
    }
}
