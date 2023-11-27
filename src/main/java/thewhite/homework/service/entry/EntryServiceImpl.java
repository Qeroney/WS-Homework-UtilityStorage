package thewhite.homework.service.entry;

import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import thewhite.homework.exception.NotFoundException;
import thewhite.homework.model.Entry;
import thewhite.homework.model.QEntry;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.SearchEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;
import thewhite.homework.utils.QPredicates;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntryServiceImpl implements EntryService {

    EntryRepository repository;

    QEntry qEntry = QEntry.entry;

    @Override
    @Transactional
    public Entry create(CreateEntryArgument argument) {
        return repository.save(Entry.builder()
                                    .name(argument.getName())
                                    .description(argument.getDescription())
                                    .links(argument.getLinks())
                                    .grades(new ArrayList<>())
                                    .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Entry> getPageEntry(SearchEntryArgument argument, Pageable pageable) {
        Predicate predicate = buildPredicate(argument);

        return repository.findAll(predicate, pageable);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(@NotNull Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Entry update(@NotNull Long id, @NotNull UpdateEntryArgument argument) {
        Entry entry = getExisting(id);

        entry.setName(argument.getName());
        entry.setDescription(argument.getDescription());
        entry.setLinks(argument.getLinks());

        return repository.save(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public Entry getExisting(@NotNull Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new NotFoundException("Запись не найдена"));
    }

    private Predicate buildPredicate(SearchEntryArgument argument) {
        return QPredicates.builder()
                          .add(argument.getName(), qEntry.name::containsIgnoreCase)
                          .add(argument.getDescription(), qEntry.description::containsIgnoreCase)
                          .buildAnd();
    }
}
