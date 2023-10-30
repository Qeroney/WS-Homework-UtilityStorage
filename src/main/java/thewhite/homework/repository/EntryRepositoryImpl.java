package thewhite.homework.repository;

import lombok.NonNull;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EntryRepositoryImpl implements EntryRepository {
    private final Map<Integer, Entry> entries = new HashMap<>();

    public void init(@NonNull Map<Integer, Entry> loadedEntries) {
        entries.putAll(loadedEntries);
    }

    @Override
    public List<Entry> foundEntriesByName(@NonNull String name) {
        return entries.values()
                      .stream()
                      .filter(entry -> entry.getName().toLowerCase().contains(name.toLowerCase()))
                      .collect(Collectors.toList());
    }

    @Override
    public Entry getEntryById(@NonNull Integer id) {
        return entries.get(id);
    }
}
