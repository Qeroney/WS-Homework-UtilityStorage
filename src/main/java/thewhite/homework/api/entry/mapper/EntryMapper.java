package thewhite.homework.api.entry.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thewhite.homework.api.entry.dto.CreateEntryDto;
import thewhite.homework.api.entry.dto.EntryDto;
import thewhite.homework.api.entry.dto.SearchEntryDto;
import thewhite.homework.api.entry.dto.UpdateEntryDto;
import thewhite.homework.model.Entry;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.SearchEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;

@Mapper
public interface EntryMapper {

    EntryMapper ENTRY_MAPPER = Mappers.getMapper(EntryMapper.class);

    EntryDto toDto(Entry entry);

    SearchEntryArgument toSearchArgument(SearchEntryDto dto);

    CreateEntryArgument toCreateArgument(CreateEntryDto dto);

    UpdateEntryArgument toUpdateArgument(UpdateEntryDto dto);
}
