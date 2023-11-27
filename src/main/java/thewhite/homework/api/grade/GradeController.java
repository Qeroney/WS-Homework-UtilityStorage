package thewhite.homework.api.grade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import thewhite.homework.action.grade.CreateGradeAction;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.api.grade.dto.SearchGradeDto;
import thewhite.homework.model.Grade;
import thewhite.homework.service.grade.GradeService;
import thewhite.homework.service.grade.argument.SearchGradeArgument;

import java.util.UUID;

import static thewhite.homework.api.grade.mapper.GradeMapper.GRADE_MAPPER;

@RequiredArgsConstructor
@RestController
@RequestMapping("grade")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Контроллер для работы с оценками")
public class GradeController {

    GradeService gradeService;

    CreateGradeAction action;

    @GetMapping("{entryId}/page")
    @ApiResponse(description = "Оценка не найдена", responseCode = "404")
    @Operation(description = "Получения списка по идентификатору записи с полезностями")
    public Page<GradeDto> getPageGrade(@PathVariable Long entryId, SearchGradeDto dto,
                                       @PageableDefault(sort = {"rating"}, direction = Sort.Direction.ASC)
                                    Pageable pageable) {

        SearchGradeArgument argument = GRADE_MAPPER.toSearchArgument(dto);
        Page<Grade> page = gradeService.getPageGrade(argument, entryId, pageable);
        return page.map(GRADE_MAPPER::toDto);
    }

    @PostMapping("create")
    @Operation(description = "Создать оценку")
    public GradeDto create(@RequestBody CreateGradeDto dto) {
        Grade execute = action.execute(GRADE_MAPPER.toCreateArgument(dto));

        return GRADE_MAPPER.toDto(execute);
    }

    @DeleteMapping("{id}/delete")
    @ApiResponse(description = "Оценка не найдена", responseCode = "404")
    @Operation(description = "Удалить оценку")
    public void delete(@PathVariable UUID id) {
        gradeService.delete(id);
    }
}
