package thewhite.homework.controller;

import thewhite.homework.service.EntryService;

public class AppController {
    private final EntryService service;

    public AppController(EntryService service) {
        this.service = service;
    }

    public void run() {
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
