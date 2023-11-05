package thewhite.homework.repository;
import thewhite.homework.model.Entry;

import java.util.List;

public interface EntryRepository {

    Entry create(Entry entry);

    Entry update(Long id, Entry entry);

    List<Entry> findEntriesByName(String name);

    Entry findEntryById(Long id);

    void deleteById(Long id);
}

