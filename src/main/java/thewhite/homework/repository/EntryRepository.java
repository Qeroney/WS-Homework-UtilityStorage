package thewhite.homework.repository;

import thewhite.homework.model.Entry;

import java.util.List;
import java.util.Map;

public interface EntryRepository {

    void init(Map<Integer, Entry> loadedEntries);

    List<Entry> foundEntriesByName(String name);

    Entry getEntryById(Integer id);
}

