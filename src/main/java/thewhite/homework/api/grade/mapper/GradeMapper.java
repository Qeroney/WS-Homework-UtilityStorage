package thewhite.homework.api.grade.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import thewhite.homework.action.grade.CreateGradeActionArgument;
import thewhite.homework.api.PageDto;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.api.grade.dto.SearchGradeDto;
import thewhite.homework.api.securityAudit.dto.SecurityAuditDto;
import thewhite.homework.model.Grade;
import thewhite.homework.model.SecurityAudit;
import thewhite.homework.service.grade.argument.SearchGradeArgument;

@Mapper
public interface GradeMapper {

    GradeDto toDto(Grade grade);

    SearchGradeArgument toSearchArgument(SearchGradeDto dto);

    CreateGradeActionArgument toCreateArgument(CreateGradeDto dto);

    @Mapping(source = "grades.content", target = "contents")
    @Mapping(source = "grades.totalElements", target = "totalElements")
    PageDto<GradeDto> toSearchResultDto(Page<Grade> grades);
}