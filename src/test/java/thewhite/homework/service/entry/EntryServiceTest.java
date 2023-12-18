package thewhite.homework.service.entry;

import com.querydsl.core.types.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Entry;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.SearchEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class EntryServiceTest {

    private final EntryRepository entryRepository = Mockito.mock(EntryRepository.class);

    private final EntryService entryService = new EntryServiceImpl(entryRepository);

    @Test
    void create() {
        //Arrange
        Entry entry = Mockito.mock(Entry.class);

        CreateEntryArgument argument = CreateEntryArgument.builder()
                                                          .name("name")
                                                          .description("desc")
                                                          .links(List.of("link1", "link2"))
                                                          .build();

        Mockito.when(entryRepository.save(any())).thenReturn(entry);

        //Act
        Entry actual = entryService.create(argument);

        //Assert
        ArgumentCaptor<Entry> entryArgumentCaptor = ArgumentCaptor.forClass(Entry.class);

        Mockito.verify(entryRepository).save(entryArgumentCaptor.capture());

        Entry expectedEntry = Entry.builder()
                                   .name("name")
                                   .links(List.of("link1", "link2"))
                                   .description("desc")
                                   .grades(new ArrayList<>())
                                   .build();

        Assertions.assertThat(entryArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(expectedEntry);
        Assertions.assertThat(actual).isEqualTo(entry);
    }

    @Test
    void delete() {
        //Arrange
        Long id = 1L;

        //Act
        entryService.delete(id);

        //Assert
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(entryRepository).deleteById(longArgumentCaptor.capture());

        Assertions.assertThat(longArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(id);
    }

    @Test
    void update() {
        //Arrange
        Long id = 1L;
        Entry entry = Mockito.mock(Entry.class);

        UpdateEntryArgument argument = UpdateEntryArgument.builder()
                                                          .name("name")
                                                          .description("desc")
                                                          .links(List.of("link1", "link2"))
                                                          .build();

        Mockito.when(entryRepository.findById(id)).thenReturn(Optional.of(Entry.builder()
                                                                               .id(id)
                                                                               .name("name")
                                                                               .links(List.of("link1", "link2"))
                                                                               .description("desc")
                                                                               .build()));
        Mockito.when(entryRepository.save(any())).thenReturn(entry);

        //Act
        Entry actual = entryService.update(id, argument);

        //Assert
        ArgumentCaptor<Entry> entryArgumentCaptor = ArgumentCaptor.forClass(Entry.class);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(entryRepository).findById(longArgumentCaptor.capture());
        Mockito.verify(entryRepository).save(entryArgumentCaptor.capture());

        Entry expectedEntry = Entry.builder()
                                   .id(id)
                                   .name("name")
                                   .description("desc")
                                   .links(List.of("link1", "link2"))
                                   .build();

        Assertions.assertThat(longArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(id);
        Assertions.assertThat(entryArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(expectedEntry);
        Assertions.assertThat(actual).isEqualTo(entry);
    }

    @Test
    void getExisting() {
        //Arrange
        Entry entry = Mockito.mock(Entry.class);
        Long id = 1L;

        Mockito.when(entryRepository.findById(id)).thenReturn(Optional.of(entry));
        //Act
        Entry actual = entryService.getExisting(id);

        //Assert
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(entryRepository).findById(longArgumentCaptor.capture());

        Assertions.assertThat(longArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .isEqualTo(id);
        Assertions.assertThat(actual).isEqualTo(entry);
    }

    @Test
    void page() {
        // Arrange
        SearchEntryArgument argument = SearchEntryArgument.builder()
                                                          .name("name")
                                                          .description("description")
                                                          .build();
        Pageable pageable = PageRequest.of(0, 10);

        Page<Entry> expectedPage = new PageImpl<>(Collections.singletonList(new Entry()));
        Mockito.when(entryRepository.findAll(any(Predicate.class), eq(pageable))).thenReturn(expectedPage);

        // Act
        Page<Entry> result = entryService.page(argument, pageable);

        // Assert
        ArgumentCaptor<Predicate> predicateCaptor = ArgumentCaptor.forClass(Predicate.class);
        Mockito.verify(entryRepository).findAll(predicateCaptor.capture(), eq(pageable));
        Predicate capturedPredicate = predicateCaptor.getValue();

        Assertions.assertThat(capturedPredicate.toString())
                  .isEqualTo("containsIc(entry1.name,name) && containsIc(entry1.description,description)");
        Assertions.assertThat(result)
                  .usingRecursiveComparison()
                  .isEqualTo(expectedPage);
    }
}