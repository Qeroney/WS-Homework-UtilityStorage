package thewhite.homework.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import thewhite.homework.exception.NotFoundException;
import thewhite.homework.model.Entry;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.service.argument.CreateEntryArgument;
import thewhite.homework.service.argument.UpdateEntryArgument;


import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntryServiceImpl implements EntryService {

    EntryRepository repository;

    AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Entry create(CreateEntryArgument argument) {
        Long id = idCounter.incrementAndGet();
        return repository.create(Entry.builder()
                                      .id(id)
                                      .name(argument.getName())
                                      .description(argument.getDescription())
                                      .link(argument.getLink())
                                      .build());
    }

    @Override
    public Page<Entry> findEntriesByName(String name, Pageable pageable) {
        return repository.findEntriesByName(name, pageable);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Entry update(Long id, UpdateEntryArgument argument) {
        return repository.update(id, Entry.builder()
                                          .id(id)
                                          .name(argument.getName())
                                          .description(argument.getDescription())
                                          .link(argument.getLink())
                                          .build());
    }

    @Override
    public Entry getExisting(Long id) {
        return Optional.ofNullable(repository.findEntryById(id))
                       .orElseThrow(() -> new NotFoundException("Запись не найдена"));
    }
}
