package thewhite.homework.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thewhite.homework.controller.dto.CreateEntryDto;
import thewhite.homework.controller.dto.EntryDto;
import thewhite.homework.controller.dto.UpdateEntryDto;
import thewhite.homework.model.Entry;
import thewhite.homework.service.argument.CreateEntryArgument;
import thewhite.homework.service.argument.UpdateEntryArgument;

import java.util.List;

@Mapper
public interface EntryMapper {

    EntryMapper ENTRY_MAPPER = Mappers.getMapper(EntryMapper.class);

    EntryDto toDto(Entry entry);

    List<EntryDto> toDtoList(List<Entry> entries);

    CreateEntryArgument toCreateArgument(CreateEntryDto dto);

    UpdateEntryArgument toUpdateArgument(UpdateEntryDto dto);
}
