package thewhite.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Entry;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.service.argument.CreateEntryArgument;
import thewhite.homework.service.argument.UpdateEntryArgument;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class EntryServiceTest {

    private EntryRepository repository;
    private EntryServiceImpl entryService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(EntryRepository.class);
        entryService = new EntryServiceImpl(repository);
    }

    @Test
    void testCreate() {
        // Arrange
        Entry entry = Entry.builder()
                           .id(1L)
                           .name("name")
                           .description("desc")
                           .link("link")
                           .build();
        CreateEntryArgument argument = CreateEntryArgument.builder()
                                                          .name("name")
                                                          .description("desc")
                                                          .link("link")
                                                          .build();
        Mockito.when(repository.create(any())).thenReturn(entry);

        // Act
        Entry result = entryService.create(argument);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("desc", result.getDescription());
        Assertions.assertEquals("link", result.getLink());

        Mockito.verify(repository).create(any(Entry.class));
    }

    @Test
    void testFoundEntries() {
        // Arrange
        String name = "name";
        List<Entry> entries = new ArrayList<>();
        entries.add(Entry.builder()
                         .id(1L)
                         .name("name")
                         .description("desc")
                         .link("link")
                         .build());
        Page<Entry> page = new PageImpl<>(entries);
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(repository.findEntriesByName(name, pageable)).thenReturn(page);

        // Act
        Page<Entry> result = entryService.findEntriesByName(name, pageable);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals("name", result.getContent().get(0).getName());
        Assertions.assertEquals("desc", result.getContent().get(0).getDescription());
        Assertions.assertEquals("link", result.getContent().get(0).getLink());

        Mockito.verify(repository).findEntriesByName(name, pageable);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        entryService.delete(id);

        // Assert
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    void testUpdate() {
        // Arrange
        Entry entry = Entry.builder()
                           .id(1L)
                           .name("name")
                           .description("desc")
                           .link("link")
                           .build();
        UpdateEntryArgument argument = UpdateEntryArgument.builder()
                                                          .name("name")
                                                          .description("desc")
                                                          .link("link")
                                                          .build();
        Mockito.when(repository.update(any(Long.class), any(Entry.class))).thenReturn(entry);

        // Act
        Entry result = entryService.update(entry.getId(), argument);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("desc", result.getDescription());
        Assertions.assertEquals("link", result.getLink());

        Mockito.verify(repository).update(any(Long.class), any(Entry.class));
    }

    @Test
    void testGetExisting() {
        // Arrange
        Long id = 1L;
        Entry entry = Entry.builder()
                           .id(id)
                           .name("name")
                           .description("desc")
                           .link("link")
                           .build();

        Mockito.when(repository.findEntryById(id)).thenReturn(entry);

        // Act
        Entry result = entryService.getExisting(id);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("desc", result.getDescription());
        Assertions.assertEquals("link", result.getLink());

        Mockito.verify(repository).findEntryById(id);
    }
}


