package thewhite.homework.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Entry;
import thewhite.homework.service.argument.CreateEntryArgument;
import thewhite.homework.service.argument.UpdateEntryArgument;

public interface EntryService {
    Entry create(CreateEntryArgument argument);

    Page<Entry> findEntriesByName(String name, Pageable pageable);

    void delete(Long id);

    Entry update(Long id, UpdateEntryArgument argument);

    Entry getExisting(Long id);
}
