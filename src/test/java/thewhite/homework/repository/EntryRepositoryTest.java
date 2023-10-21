package thewhite.homework.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import thewhite.homework.file.FileLoader;
import thewhite.homework.file.JsonFileLoader;
import thewhite.homework.model.Entry;

import java.util.List;

public class EntryRepositoryTest {

    @Mock
    private FileLoader fileLoader;

    private EntryRepository repository;

    @BeforeEach
    public void setUp() {
        fileLoader = new JsonFileLoader("entryJson/entry.json");
        repository = new EntryRepositoryImpl(fileLoader);
    }

    @Test
    public void foundEntriesTest() {
        //Arrange
        String expectedStr = "Name1";

        //Act
        List<Entry> entries = repository.foundEntriesByName(expectedStr);

        //Assert
        Assertions.assertEquals(1, entries.size());
        Assertions.assertEquals(expectedStr, entries.get(0).getName());
    }

    @Test
    public void getEntriesTest() {
        //Arrange
        int existingId = 1;

        //Act
        Entry entry = repository.getEntryById(existingId);

        //Assert
        Assertions.assertEquals(existingId, entry.getId());
    }
}
