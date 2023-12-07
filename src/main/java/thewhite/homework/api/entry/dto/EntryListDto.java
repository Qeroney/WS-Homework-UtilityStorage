package thewhite.homework.api.entry.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class EntryListDto {

    Long id;

    String name;

    String description;

    List<String> links;
}
