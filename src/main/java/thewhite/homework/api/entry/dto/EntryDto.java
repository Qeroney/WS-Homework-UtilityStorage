package thewhite.homework.api.entry.dto;

import lombok.*;
import thewhite.homework.api.grade.dto.GradeDto;

import java.util.List;

@Value
@Builder
public class EntryDto {

    Long id;

    String name;

    String description;

    List<String> links;

    List<GradeDto> grades;
}
