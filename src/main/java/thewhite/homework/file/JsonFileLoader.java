package thewhite.homework.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import thewhite.homework.model.Entry;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonFileLoader implements FileLoader {
    private String filePath;

    @Autowired
    public JsonFileLoader(@Value("${file.name}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Map<Integer, Entry> loadEntriesFromFile() {
        Map<Integer, Entry> entries = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Entry> entryList = mapper.readValue(new File(filePath), new TypeReference<List<Entry>>() {});
            for (Entry entry : entryList) {
                entries.put(entry.getId(), entry);
            }
        } catch (IOException e) {
            System.err.println("Failed to load entries from file: " + e.getMessage());
            e.printStackTrace(System.err);
        }
        return entries;
    }
}
