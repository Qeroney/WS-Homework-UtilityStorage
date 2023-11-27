package thewhite.homework.api.grade.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thewhite.homework.action.grade.CreateGradeActionArgument;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.api.grade.dto.SearchGradeDto;
import thewhite.homework.model.Grade;
import thewhite.homework.service.grade.argument.SearchGradeArgument;

@Mapper
public interface GradeMapper {

    GradeMapper GRADE_MAPPER = Mappers.getMapper(GradeMapper.class);

    GradeDto toDto(Grade grade);

    SearchGradeArgument toSearchArgument(SearchGradeDto dto);

    CreateGradeActionArgument toCreateArgument(CreateGradeDto dto);
}