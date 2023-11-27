package thewhite.homework.api.entry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import thewhite.homework.api.entry.dto.CreateEntryDto;
import thewhite.homework.api.entry.dto.EntryDto;
import thewhite.homework.api.entry.dto.SearchEntryDto;
import thewhite.homework.api.entry.dto.UpdateEntryDto;
import thewhite.homework.model.Entry;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.entry.argument.SearchEntryArgument;

import static thewhite.homework.api.entry.mapper.EntryMapper.ENTRY_MAPPER;

@RequiredArgsConstructor
@RestController
@RequestMapping("entry")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Контроллер для работы с хранилищем")
public class EntryController {

    EntryService entryService;

    @PostMapping("create")
    @Operation(description = "Создать запись")
    public EntryDto create(@RequestBody CreateEntryDto dto) {
        Entry create = entryService.create(ENTRY_MAPPER.toCreateArgument(dto));

        return ENTRY_MAPPER.toDto(create);
    }

    @PutMapping("{id}/update")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    @Operation(description = "Обновить запись")
    public EntryDto update(@PathVariable Long id, @RequestBody UpdateEntryDto dto) {
        Entry update = entryService.update(id, ENTRY_MAPPER.toUpdateArgument(dto));

        return ENTRY_MAPPER.toDto(update);
    }

    @GetMapping("page")
    @ApiResponse(description = "Записи не найдены", responseCode = "404")
    @Operation(description = "Получить page")
    public Page<EntryDto> getPageEntry(SearchEntryDto dto,
                                       @PageableDefault(sort = {"name", "description"})
                                       Pageable pageable) {

        SearchEntryArgument argument = ENTRY_MAPPER.toSearchArgument(dto);
        Page<Entry> page = entryService.getPageEntry(argument, pageable);
        return page.map(ENTRY_MAPPER::toDto);
    }

    @GetMapping("{id}/get")
    @Operation(description = "Получить запись по id")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    public EntryDto get(@PathVariable Long id) {
        Entry existing = entryService.getExisting(id);

        return ENTRY_MAPPER.toDto(existing);
    }

    @DeleteMapping("{id}/delete")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    @Operation(description = "Удалить запись по id")
    public void delete(@PathVariable Long id) {
        entryService.delete(id);
    }
}
