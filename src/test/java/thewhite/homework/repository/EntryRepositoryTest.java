package thewhite.homework.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thewhite.homework.model.Entry;

import java.util.ArrayList;
import java.util.List;

class EntryRepositoryTest {

    private EntryRepository repository;

    @BeforeEach
    void setup() {
        repository = new EntryRepositoryImpl();
    }

    @Test
    void testCreate() {
        //Arrange
        Entry entry = Entry.builder()
                           .id(1L)
                           .name("name")
                           .description("desc")
                           .link("link")
                           .build();

        //Act
        Entry actual = repository.create(entry);

        //Assert
        Assertions.assertEquals(actual, entry);
    }

    @Test
    void testUpdate() {
        //Arrange
        Long id = 1L;
        Entry entry = new Entry(1L, "name", "desc", "link");
        repository.create(entry);

        //Act
        Entry actual = repository.update(id, entry);

        //Assert
        Assertions.assertEquals(actual, entry);
    }

    @Test
    void testFindEntriesByName() {
        //Arrange
        String name = "name";
        List<Entry> entries = new ArrayList<>();
        Entry entry1 = Entry.builder()
                            .id(1L)
                            .name(name)
                            .description("desc1")
                            .link("link1")
                            .build();
        Entry entry2 = Entry.builder()
                            .id(2L)
                            .name(name)
                            .description("desc2")
                            .link("link2")
                            .build();
        entries.add(entry1);
        entries.add(entry2);
        repository.create(entry1);
        repository.create(entry2);

        //Act
        List<Entry> actual = repository.findEntriesByName(name);

        //Assert
        Assertions.assertEquals(actual, entries);
    }

    @Test
    void testFindEntryById() {
        //Arrange
        Long id = 1L;
        Entry entry = Entry.builder()
                           .id(id)
                           .name("name")
                           .description("desc")
                           .link("link")
                           .build();
        repository.create(entry);

        //Act
        Entry actual = repository.findEntryById(id);

        //Assert
        Assertions.assertEquals(actual, entry);
    }

    @Test
    void testDelete() {
        //Arrange
        Long id = 1L;
        Entry entry = Entry.builder()
                           .id(id)
                           .name("name")
                           .description("desc")
                           .link("link")
                           .build();
        repository.create(entry);

        //Act
        repository.deleteById(id);

        //Assert
        Entry actual = repository.findEntryById(id);
        Assertions.assertNull(actual);
    }
}
