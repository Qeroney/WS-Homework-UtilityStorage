package thewhite.homework;

import thewhite.homework.controller.AppController;
import thewhite.homework.file.FileLoader;
import thewhite.homework.file.JsonFileLoader;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.repository.EntryRepositoryImpl;
import thewhite.homework.service.EntryService;
import thewhite.homework.service.EntryServiceImpl;

public class App {
    public static void main(String[] args) {
        FileLoader fileLoader = new JsonFileLoader("entryJson/entry.json");
        EntryRepository repository = new EntryRepositoryImpl(fileLoader);
        EntryService service = new EntryServiceImpl(repository);
        AppController controller = new AppController(service);
        controller.run();
    }
}
