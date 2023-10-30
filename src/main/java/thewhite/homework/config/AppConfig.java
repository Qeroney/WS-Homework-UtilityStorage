package thewhite.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import thewhite.homework.controller.AppController;
import thewhite.homework.file.FileLoader;
import thewhite.homework.repository.EntryRepository;

@Configuration
public class AppConfig implements CommandLineRunner {
    private final AppController appController;
    private final FileLoader fileLoader;
    private final EntryRepository entryRepository;

    @Autowired
    public AppConfig(AppController appController, FileLoader fileLoader, EntryRepository entryRepository) {
        this.appController = appController;
        this.fileLoader = fileLoader;
        this.entryRepository = entryRepository;
    }

    @Override
    public void run(String... args) {
        entryRepository.init(fileLoader.loadEntriesFromFile());
        appController.run();
    }
}
