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
import thewhite.homework.api.PageDto;
import thewhite.homework.api.entry.dto.*;
import thewhite.homework.api.entry.mapper.EntryMapper;
import thewhite.homework.model.Entry;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.entry.argument.SearchEntryArgument;

@RequiredArgsConstructor
@RestController
@RequestMapping("entry")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Контроллер для работы с хранилищем")
public class EntryController {

    EntryService entryService;

    EntryMapper entryMapper;

    @PostMapping("create")
    @Operation(description = "Создать запись")
    public EntryDto create(@RequestBody CreateEntryDto dto) {
        Entry create = entryService.create(entryMapper.toCreateArgument(dto));

        return entryMapper.toDto(create);
    }

    @PutMapping("{id}/update")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    @Operation(description = "Обновить запись")
    public EntryDto update(@PathVariable Long id, @RequestBody UpdateEntryDto dto) {
        Entry update = entryService.update(id, entryMapper.toUpdateArgument(dto));

        return entryMapper.toDto(update);
    }

    @GetMapping("page")
    @Operation(description = "Получить список записей с пейджинацией и сортировкой")
    public PageDto<EntryListDto> getPageEntry(SearchEntryDto dto,
                                              @PageableDefault(sort = {"name", "description"}) Pageable pageable) {

        SearchEntryArgument argument = entryMapper.toSearchArgument(dto);
        Page<Entry> page = entryService.getPageEntry(argument, pageable);
        Page<EntryListDto> entries = page.map(entryMapper::toDtoList);
        return new PageDto<>(entries.getContent(), page.getTotalElements());
    }

    @GetMapping("{id}/get")
    @Operation(description = "Получить запись по id")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    public EntryDto get(@PathVariable Long id) {
        Entry existing = entryService.getExisting(id);

        return entryMapper.toDto(existing);
    }

    @DeleteMapping("{id}/delete")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    @Operation(description = "Удалить запись по id")
    public void delete(@PathVariable Long id) {
        entryService.delete(id);
    }
}
