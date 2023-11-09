package thewhite.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import thewhite.homework.controller.dto.CreateEntryDto;
import thewhite.homework.controller.dto.EntryDto;
import thewhite.homework.controller.dto.UpdateEntryDto;
import thewhite.homework.controller.mapper.EntryMapper;
import thewhite.homework.model.Entry;
import thewhite.homework.service.EntryService;

import java.util.List;

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

    @GetMapping("find/{name}")
    @ApiResponse(description = "Запись не найдена", responseCode = "404")
    @Operation(description = "Получить запись по name")
    public Page<EntryDto> findEntryByName(@PathVariable String name,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Entry> entries = entryService.findEntriesByName(name, pageable);

        List<EntryDto> dtos = entryMapper.toDtoList(entries.getContent());
        return new PageImpl<>(dtos, pageable, entries.getTotalElements());
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
