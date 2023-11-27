package thewhite.homework.service.entry.argument;

import lombok.Builder;
import lombok.Value;
import thewhite.homework.model.Grade;

import java.util.List;

@Value
@Builder
public class CreateEntryArgument {

    String name;

    String description;

    List<String> links;

    List<Grade> grades;
}
