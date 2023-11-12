package thewhite.homework.api.grade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import thewhite.homework.action.create.CreateGradeAction;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.api.grade.mapper.GradeMapper;
import thewhite.homework.model.Grade;
import thewhite.homework.service.grade.GradeService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("grade")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Контроллер для работы с оценками")
public class GradeController {

    GradeService gradeService;

    CreateGradeAction action;

    GradeMapper gradeMapper;

    @GetMapping("{entryId}/getAll")
    @Operation(description = "Получения списка по идентификатору записи с полезностями")
    public List<GradeDto> findByEntryId(@PathVariable Long entryId) {
        List<Grade> existing = gradeService.getAllExisting(entryId);

        return gradeMapper.toDtoList(existing);
    }

    @PostMapping("create")
    @Operation(description = "Создать оценку")
    public GradeDto create(@RequestBody CreateGradeDto dto) {
        Grade execute = action.execute(gradeMapper.toCreateArgument(dto));

        return gradeMapper.toDto(execute);
    }

    @DeleteMapping("{id}/delete")
    @Operation(description = "Удалить оценку")
    public void delete(@PathVariable UUID id) {
        gradeService.delete(id);
    }
}
