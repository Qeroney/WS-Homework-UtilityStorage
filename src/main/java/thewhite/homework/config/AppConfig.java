package thewhite.homework.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import thewhite.homework.controller.AppController;
import thewhite.homework.file.FileLoader;
import thewhite.homework.repository.EntryRepository;

@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AppConfig implements CommandLineRunner {

    AppController appController;

    FileLoader fileLoader;

    EntryRepository entryRepository;

    @Override
    public void run(String... args) {
        entryRepository.init(fileLoader.loadEntriesFromFile());
        appController.run();
    }
}
