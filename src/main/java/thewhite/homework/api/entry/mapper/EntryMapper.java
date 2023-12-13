package thewhite.homework.api.entry.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import thewhite.homework.api.PageDto;
import thewhite.homework.api.entry.dto.*;
import thewhite.homework.model.Entry;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.SearchEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;

@Mapper
public interface EntryMapper {

    EntryDto toDto(Entry entry);

    SearchEntryArgument toSearchArgument(SearchEntryDto dto);

    CreateEntryArgument toCreateArgument(CreateEntryDto dto);

    UpdateEntryArgument toUpdateArgument(UpdateEntryDto dto);

    @Mapping(source = "entries.content", target = "contents")
    @Mapping(source = "entries.totalElements", target = "totalElements")
    PageDto<EntryListDto> toSearchResultDto(Page<Entry> entries);
}