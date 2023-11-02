package thewhite.homework.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import thewhite.homework.config.AppConfig;
import thewhite.homework.service.EntryService;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AppController implements CommandLineRunner {

    EntryService service;

    AppConfig config;

    @Override
    public void run(String... args) {
        config.initRepo();
        boolean isRunning = true;

        while (isRunning) {
            try {
                int choice = service.getUserChoice();
                switch (choice) {
                    case 1: {
                        service.printEntryById();
                        break;
                    }
                    case 2: {
                        service.findEntriesByName();
                        break;
                    }
                    case 3: {
                        isRunning = false;
                        break;
                    }
                    default: {
                        System.out.println("Unknown number of command ");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
