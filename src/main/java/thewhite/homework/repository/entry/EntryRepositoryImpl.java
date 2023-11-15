package thewhite.homework.repository.entry;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntryRepositoryImpl implements EntryRepository {
    Map<Long, Entry> entries = new HashMap<>();
    AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Entry save(Entry entry) {
        long id = idCounter.incrementAndGet();
        entry.setId(id);
        entries.put(id, entry);
        return entry;
    }

    @Override
    public Entry update(Long id, Entry entry) {
        if (entries.containsKey(id)) {
            entries.put(id, entry);
            return entry;
        }
        return null;
    }

    @Override
    public Page<Entry> findByName(String name, Pageable pageable) {
        List<Entry> filteredEntries = entries.values()
                                             .stream()
                                             .filter(entry -> entry.getName().toLowerCase().contains(name.toLowerCase()))
                                             .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredEntries.size());

        return new PageImpl<>(filteredEntries.subList(start, end), pageable, filteredEntries.size());
    }

    @Override
    public Entry findById(Long id) {
        return entries.get(id);
    }

    @Override
    public void deleteById(Long id) {
        entries.remove(id);
    }
}
