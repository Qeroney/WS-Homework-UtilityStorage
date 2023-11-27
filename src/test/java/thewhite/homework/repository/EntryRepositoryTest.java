package thewhite.homework.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Entry;

class EntryRepositoryTest {
//
//    private EntryRepository repository;
//
//    @BeforeEach
//    void setup() {
//        repository = new EntryRepositoryImpl();
//    }
//
//    @Test
//    void testCreate() {
//        //Arrange
//        Entry entry = Entry.builder()
//                           .id(1L)
//                           .name("name")
//                           .description("desc")
//                           .link("link")
//                           .build();
//
//        //Act
//        Entry actual = repository.save(entry);
//
//        //Assert
//        Assertions.assertEquals(actual, entry);
//    }
//
//    @Test
//    void testUpdate() {
//        //Arrange
//        Long id = 1L;
//        Entry entry = Entry.builder()
//                           .id(id)
//                           .name("name")
//                           .description("desc")
//                           .link("link")
//                           .build();
//        repository.save(entry);
//
//        //Act
//        Entry actual = repository.update(id, entry);
//
//        //Assert
//        Assertions.assertEquals(actual, entry);
//    }
//
//    @Test
//    void testFindEntriesByName() {
//        //Arrange
//        String name = "name";
//        Entry entry1 = Entry.builder()
//                            .id(1L)
//                            .name(name)
//                            .description("desc1")
//                            .link("link1")
//                            .build();
//        Entry entry2 = Entry.builder()
//                            .id(2L)
//                            .name(name)
//                            .description("desc2")
//                            .link("link2")
//                            .build();
//        repository.save(entry1);
//        repository.save(entry2);
//
//        Pageable pageable = PageRequest.of(0, 10);
//
//        //Act
//        Page<Entry> actual = repository.findByName(name, pageable);
//
//        //Assert
//        Assertions.assertTrue(actual.getContent().contains(entry1));
//        Assertions.assertTrue(actual.getContent().contains(entry2));
//    }
//
//    @Test
//    void testFindEntryById() {
//        //Arrange
//        Long id = 1L;
//        Entry entry = Entry.builder()
//                           .id(id)
//                           .name("name")
//                           .description("desc")
//                           .link("link")
//                           .build();
//        repository.save(entry);
//
//        //Act
//        Entry actual = repository.findById(id);
//
//        //Assert
//        Assertions.assertEquals(actual, entry);
//    }
//
//    @Test
//    void testDelete() {
//        //Arrange
//        Long id = 1L;
//        Entry entry = Entry.builder()
//                           .id(id)
//                           .name("name")
//                           .description("desc")
//                           .link("link")
//                           .build();
//        repository.save(entry);
//
//        //Act
//        repository.deleteById(id);
//
//        //Assert
//        Entry actual = repository.findById(id);
//        Assertions.assertNull(actual);
//    }
}
