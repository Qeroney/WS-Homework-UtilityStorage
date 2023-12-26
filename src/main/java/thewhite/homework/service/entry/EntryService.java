package thewhite.homework.service.entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Entry;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.SearchEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;

public interface EntryService {
    Entry create(CreateEntryArgument argument);

    Page<Entry> page(SearchEntryArgument argument, Pageable pageable);

    void delete(Long id);

    Entry update(Long id, UpdateEntryArgument argument);

    Entry getExisting(Long id);

    EntryStatistics getEntryStatistics();
}
