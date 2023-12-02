package thewhite.homework.service.entry.argument;

import lombok.*;
import thewhite.homework.model.Grade;

import java.util.List;

@Value
@Builder
public class CreateEntryArgument {

    String name;

    String description;

    List<String> links;
}
