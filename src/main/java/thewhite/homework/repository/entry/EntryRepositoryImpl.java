package thewhite.homework.repository.entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EntryRepositoryImpl implements EntryRepository {
    private final Map<Long, Entry> entries = new HashMap<>();

    @Override
    public Entry save(Entry entry) {
        entries.put(entry.getId(), entry);
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
