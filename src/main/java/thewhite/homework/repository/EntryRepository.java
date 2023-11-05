package thewhite.homework.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Entry;

import java.util.List;

public interface EntryRepository {

    Entry create(Entry entry);

    Entry update(Long id, Entry entry);

    Page<Entry> findEntriesByName(String name, Pageable pageable);

    Entry findEntryById(Long id);

    void deleteById(Long id);
}

