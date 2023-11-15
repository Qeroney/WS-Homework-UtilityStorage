package thewhite.homework.api.grade.mapper;

import org.mapstruct.Mapper;
import thewhite.homework.action.create.CreateGradeActionArgument;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.model.Grade;

import java.util.List;

@Mapper
public interface GradeMapper {

    GradeDto toDto(Grade grade);

    List<GradeDto> toDtoList(List<Grade> gradeList);

    CreateGradeActionArgument toCreateArgument(CreateGradeDto dto);
}
