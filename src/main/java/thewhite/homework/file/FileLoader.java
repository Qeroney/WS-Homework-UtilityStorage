package thewhite.homework.file;

import thewhite.homework.model.Entry;

import java.util.Map;

public interface FileLoader {
    Map<Integer, Entry> loadEntriesFromFile();
}
