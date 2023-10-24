package thewhite.homework.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import thewhite.homework.file.FileLoader;
import thewhite.homework.file.JsonFileLoader;
import thewhite.homework.model.Entry;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EntryRepositoryTest {

    private FileLoader fileLoader;
    private EntryRepository repository;

    @Before
    public void setUp() {
        fileLoader = Mockito.spy(new JsonFileLoader("src/test/resources/entryTest.json"));
        repository = new EntryRepositoryImpl(fileLoader);
        Mockito.verify(fileLoader).loadEntriesFromFile();
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
