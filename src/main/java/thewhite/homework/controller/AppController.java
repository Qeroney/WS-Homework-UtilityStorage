package thewhite.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import thewhite.homework.service.EntryService;

@Component
@RequiredArgsConstructor
public class AppController implements CommandLineRunner {

    private final EntryService service;

    @Override
    public void run(String... args) {
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
