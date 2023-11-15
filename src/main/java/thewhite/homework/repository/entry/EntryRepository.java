package thewhite.homework.repository.entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Entry;

public interface EntryRepository {

    Entry save(Entry entry);

    Entry update(Long id, Entry entry);

    Page<Entry> findByName(String name, Pageable pageable);

    Entry findById(Long id);

    void deleteById(Long id);
}

