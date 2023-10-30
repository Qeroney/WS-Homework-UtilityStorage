package thewhite.homework.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import thewhite.homework.file.FileLoader;
import thewhite.homework.model.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class EntryRepositoryImplTest {

    @MockBean
    private FileLoader fileLoader;

    private EntryRepository repository;

    @Before
    public void setUp() {
        Map<Integer, Entry> mockEntries = new HashMap<>();
        mockEntries.put(1, Entry.builder()
                                .id(1)
                                .name("Name1")
                                .description("Description1")
                                .link("Link1")
                                .build());
        Mockito.when(fileLoader.loadEntriesFromFile()).thenReturn(mockEntries);
        repository = new EntryRepositoryImpl();
        repository.init(mockEntries);
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
