package thewhite.homework.service;

import thewhite.homework.model.Entry;
import thewhite.homework.service.argument.CreateEntryArgument;
import thewhite.homework.service.argument.UpdateEntryArgument;

import java.util.List;

public interface EntryService {
    Entry create(CreateEntryArgument argument);

    List<Entry> findEntriesByName(String name);

    void delete(Long id);

    Entry update(Long id, UpdateEntryArgument argument);

    Entry getExisting(Long id);
}
