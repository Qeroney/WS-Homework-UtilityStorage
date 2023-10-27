package thewhite.homework.service;

import thewhite.homework.model.Entry;
import thewhite.homework.repository.EntryRepository;

import java.util.List;
import java.util.Scanner;

public class EntryServiceImpl implements EntryService {
    private final EntryRepository repository;

    public EntryServiceImpl(EntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void findEntriesByName() {
        System.out.println("Enter string: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        List<Entry> entries = repository.foundEntriesByName(name);
        if (!entries.isEmpty()) {
            for (Entry entry : entries) {
                System.out.println(entry);
            }
        } else {
            System.out.println("Entries not found");
        }
    }

    @Override
    public void printEntryById() {
        System.out.println("Enter ID:");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        Entry entry = repository.getEntryById(id);
        if (entry != null) {
            System.out.println(entry);
        } else {
            System.out.println("Entry not found");
        }
    }

    @Override
    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Display an entry");
        System.out.println("2 - Search entries");
        System.out.println("3 - Exit");
        return Integer.parseInt(scanner.nextLine());
    }
}
