package thewhite.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thewhite.homework.file.FileLoader;
import thewhite.homework.file.JsonFileLoader;
import thewhite.homework.model.Entry;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntryRepositoryTest {

    private EntryRepository repository;

    private FileLoader jsonFileLoader;

    @BeforeEach
    public void setUp() {
        jsonFileLoader = new JsonFileLoader("src/test/resources/entryTest.json");
        repository = new EntryRepositoryImpl();
        repository.init(jsonFileLoader.loadEntriesFromFile());
    }

    @Test
    public void foundEntriesTest() {
        //Arrange
        String expectedStr = "Name1";

        //Act
        List<Entry> entries = repository.foundEntriesByName(expectedStr);

        //Assert
        assertEquals(1, entries.size());
        assertEquals(expectedStr, entries.get(0).getName());
    }

    @Test
    public void getEntriesTest() {
        //Arrange
        int existingId = 1;

        //Act
        Entry entry = repository.getEntryById(existingId);

        //Assert
        assertEquals(existingId, entry.getId());
    }
}
