package thewhite.homework.repository;

import thewhite.homework.model.Entry;

import java.util.List;

public interface EntryRepository {
    List<Entry> foundEntriesByName(String name);

    Entry getEntryById(int id);
}

