package thewhite.homework.api.grade.mapper;

import org.mapstruct.Mapper;
import thewhite.homework.action.grade.CreateGradeActionArgument;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.api.grade.dto.SearchGradeDto;
import thewhite.homework.model.Grade;
import thewhite.homework.service.grade.argument.SearchGradeArgument;

@Mapper
public interface GradeMapper {

    GradeDto toDto(Grade grade);

    SearchGradeArgument toSearchArgument(SearchGradeDto dto);

    CreateGradeActionArgument toCreateArgument(CreateGradeDto dto);
}