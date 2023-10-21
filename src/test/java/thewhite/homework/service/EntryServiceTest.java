package thewhite.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.model.Entry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EntryServiceTest {
    @Mock
    private EntryRepository repository;

    private EntryService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new EntryServiceImpl(repository);
    }

    @Test
    public void testFindEntryById() {
        // Arrange
        int id = 1;
        Entry entry = new Entry(id, "Name1", "Description1", "Link1");
        when(repository.getEntryById(id)).thenReturn(entry);

        // Перехватываем вывод, который генерится в методе
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        // Иммитируем ввод пользователя
        InputStream inputStream = new ByteArrayInputStream(String.valueOf(id).getBytes());
        InputStream originalIn = System.in;
        System.setIn(inputStream);

        // Act
        service.printEntryById();

        // Assert
        String expectedOutput = "Enter ID:" + System.lineSeparator() + entry + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());

        // Проверяем что метод был вызван 1 раз
        verify(repository, Mockito.only()).getEntryById(id);

        //Возвращаем потоки в исходное состояние, чтобы не влияли на другие тесты
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    public void testFindEntriesByName() {
        //Arrange
        String name = "Name1";
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, "Name1", "Description1", "Link1"));
        entries.add(new Entry(2, "Name2", "Description2", "Link2"));
        Mockito.when(repository.foundEntriesByName(name)).thenReturn(entries);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        InputStream inputStream = new ByteArrayInputStream(name.getBytes());
        InputStream originalIn = System.in;
        System.setIn(inputStream);

        //Act
        service.findEntriesByName();

        //Assert
        // Создаем поток , map преобразование каждой Entry в String , потом собираю и использую Collectors.joining() для объеденения строк в одну + разделитель строк
        String expectedOutput = "Enter string: " + System.lineSeparator() +
                                entries.stream()
                                       .map(Entry::toString)
                                       .collect(Collectors.joining(System.lineSeparator())) +
                                System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());

        verify(repository, Mockito.only()).foundEntriesByName(name);

        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}
