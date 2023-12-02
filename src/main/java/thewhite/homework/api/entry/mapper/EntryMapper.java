package thewhite.homework.api.entry.mapper;

import org.mapstruct.Mapper;
import thewhite.homework.api.entry.dto.*;
import thewhite.homework.model.Entry;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.SearchEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;

@Mapper
public interface EntryMapper {

    EntryDto toDto(Entry entry);

    EntryListDto toDtoList(Entry entry);

    SearchEntryArgument toSearchArgument(SearchEntryDto dto);

    CreateEntryArgument toCreateArgument(CreateEntryDto dto);

    UpdateEntryArgument toUpdateArgument(UpdateEntryDto dto);
}