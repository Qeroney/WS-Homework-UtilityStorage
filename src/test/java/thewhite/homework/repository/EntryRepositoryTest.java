package thewhite.homework.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import thewhite.homework.file.JsonFileLoader;
import thewhite.homework.model.Entry;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class EntryRepositoryTest {

    private EntryRepository repository;

    private JsonFileLoader jsonFileLoader;

    @Before
    public void setUp() {
        jsonFileLoader = Mockito.spy(jsonFileLoader = new JsonFileLoader());
        jsonFileLoader.setFilePath("src/test/resources/entryTest.json");
        Map<Integer, Entry> entries = jsonFileLoader.loadEntriesFromFile();
        Mockito.verify(jsonFileLoader).loadEntriesFromFile();
        repository = new EntryRepositoryImpl();
        repository.init(entries);
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
