package thewhite.homework.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thewhite.homework.model.Entry;

import java.util.Map;

public class JsonFileLoaderTest {

    @Test
    public void JsonReaderTest() {
        //Arrange
        JsonFileLoader jsonFileLoader = new JsonFileLoader("entryJson/entry.json");

        //Act
        Map<Integer, Entry> entries = jsonFileLoader.loadEntriesFromFile();

        //Assert
        Assertions.assertEquals(5, entries.size());
        Assertions.assertEquals("Name1", entries.get(1).getName());
        Assertions.assertEquals("Description1", entries.get(1).getDescription());
        Assertions.assertEquals("Link1", entries.get(1).getLink());
    }
}
