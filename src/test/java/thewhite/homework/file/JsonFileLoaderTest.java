package thewhite.homework.file;

import org.junit.jupiter.api.Test;
import thewhite.homework.model.Entry;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFileLoaderTest {

    @Test
    void jsonReaderTest() {
        //Arrange
        JsonFileLoader jsonFileLoader = new JsonFileLoader("src/main/resources/entry.json");

        //Act
        Map<Integer, Entry> entries = jsonFileLoader.loadEntriesFromFile();

        //Assert
        assertEquals(5, entries.size());
        assertEquals("Name1", entries.get(1).getName());
        assertEquals("Description1", entries.get(1).getDescription());
        assertEquals("Link1", entries.get(1).getLink());
    }
}
