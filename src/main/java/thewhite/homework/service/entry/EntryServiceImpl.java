package thewhite.homework.service.entry;

import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import thewhite.homework.aspect.annotation.LogStatistics;
import thewhite.homework.exception.NotFoundException;
import thewhite.homework.model.Entry;
import thewhite.homework.model.QEntry;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.SearchEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;
import thewhite.homework.utils.QPredicates;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntryServiceImpl implements EntryService {

    EntryRepository repository;

    QEntry qEntry = QEntry.entry;

    @Override
    @Transactional
    @LogStatistics
    public Entry create(@NonNull CreateEntryArgument argument) {
        return repository.save(Entry.builder()
                                    .name(argument.getName())
                                    .description(argument.getDescription())
                                    .links(argument.getLinks())
                                    .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Entry> page(SearchEntryArgument argument, Pageable pageable) {
        Predicate predicate = buildPredicate(argument);

        return repository.findAll(predicate, pageable);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @LogStatistics
    public void delete(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @LogStatistics
    public Entry update(@NonNull Long id, @NonNull UpdateEntryArgument argument) {
        Entry entry = getExisting(id);

        entry.setName(argument.getName());
        entry.setDescription(argument.getDescription());
        entry.setLinks(argument.getLinks());

        return repository.save(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public Entry getExisting(@NonNull Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new NotFoundException("Запись не найдена"));
    }

    @Override
    @Transactional(readOnly = true)
    public EntryStatistics getEntryStatistics() {
        long totalEntries = repository.count();
        long withoutGrades = repository.getEntriesWithoutGrades();
        long aboveFourEntries = repository.getAboveFourEntries();
        long noLessThanFourEntries = repository.getNoLessThanFourEntries();
        long entriesWithAverageGradeEqualsFive = repository.entriesWithAverageGradeEqualsFive();

        Double percentageNoLessThanFourEntries = ((double) noLessThanFourEntries / totalEntries) * 100;
        Double percentageAboveFourEntries = ((double) aboveFourEntries / totalEntries) * 100;
        Double percentageEntriesWithAverageGradeEqualsFive = ((double) entriesWithAverageGradeEqualsFive / totalEntries) * 100;

        return EntryStatistics.builder()
                              .entriesWithoutGrades(withoutGrades)
                              .maxRatingEntries(entriesWithAverageGradeEqualsFive)
                              .aboveFourEntries(aboveFourEntries)
                              .noLessThanFourEntries(noLessThanFourEntries)
                              .totalEntries(totalEntries)
                              .noLessThanFourPercentage(percentageNoLessThanFourEntries)
                              .maxRatingPercentage(percentageEntriesWithAverageGradeEqualsFive)
                              .aboveFourPercentage(percentageAboveFourEntries)
                              .build();
    }


    private Predicate buildPredicate(SearchEntryArgument argument) {
        return QPredicates.builder()
                          .add(argument.getName(), qEntry.name::containsIgnoreCase)
                          .add(argument.getDescription(), qEntry.description::containsIgnoreCase)
                          .buildAnd();
    }
}