package thewhite.homework.repository;

import thewhite.homework.file.FileLoader;
import thewhite.homework.model.Entry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntryRepositoryImpl implements EntryRepository {
    private final Map<Integer, Entry> entries;

    public EntryRepositoryImpl(FileLoader loader) {
        this.entries = loader.loadEntriesFromFile();
    }

    @Override
    public List<Entry> foundEntriesByName(String name) {
        return entries.values()
                      .stream()
                      .filter(entry -> entry.getName().toLowerCase().contains(name.toLowerCase()))
                      .collect(Collectors.toList());
    }

    @Override
    public Entry getEntryById(Integer id) {
        return entries.get(id);
    }
}
