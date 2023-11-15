package thewhite.homework.api.entry.mapper;

import org.mapstruct.Mapper;
import thewhite.homework.api.entry.dto.CreateEntryDto;
import thewhite.homework.api.entry.dto.EntryDto;
import thewhite.homework.api.entry.dto.UpdateEntryDto;
import thewhite.homework.model.Entry;
import thewhite.homework.service.entry.argument.CreateEntryArgument;
import thewhite.homework.service.entry.argument.UpdateEntryArgument;

import java.util.List;

@Mapper
public interface EntryMapper {

    EntryDto toDto(Entry entry);

    List<EntryDto> toDtoList(List<Entry> entries);

    CreateEntryArgument toCreateArgument(CreateEntryDto dto);

    UpdateEntryArgument toUpdateArgument(UpdateEntryDto dto);
}
