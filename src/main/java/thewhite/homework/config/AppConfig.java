package thewhite.homework.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import thewhite.homework.file.FileLoader;
import thewhite.homework.repository.EntryRepository;

@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AppConfig {

    FileLoader fileLoader;

    EntryRepository entryRepository;

    public void initRepo() {
        entryRepository.init(fileLoader.loadEntriesFromFile());
    }
}
