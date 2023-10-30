package thewhite.homework.file;

import org.junit.Test;
import thewhite.homework.model.Entry;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonFileLoaderTest {

    @Test
    public void JsonReaderTest() {
        //Arrange
        JsonFileLoader jsonFileLoader = new JsonFileLoader();
        jsonFileLoader.setFilePath("src/main/resources/entry.json");

        //Act
        Map<Integer, Entry> entries = jsonFileLoader.loadEntriesFromFile();

        //Assert
        assertEquals(5, entries.size());
        assertEquals("Name1", entries.get(1).getName());
        assertEquals("Description1", entries.get(1).getDescription());
        assertEquals("Link1", entries.get(1).getLink());
    }
}
