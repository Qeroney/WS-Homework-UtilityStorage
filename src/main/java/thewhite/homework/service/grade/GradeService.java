package thewhite.homework.service.grade;

import thewhite.homework.model.Grade;
import thewhite.homework.service.grade.argument.CreateGradeArgument;

import java.util.List;
import java.util.UUID;

public interface GradeService {

    Grade create(CreateGradeArgument argument);

    void delete(UUID id);

    List<Grade> getAllExisting(Long entryId);
}
