package thewhite.homework.service.grade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.Grade;
import thewhite.homework.service.grade.argument.CreateGradeArgument;
import thewhite.homework.service.grade.argument.SearchGradeArgument;

import java.util.UUID;

public interface GradeService {

    Grade create(CreateGradeArgument argument);

    void delete(UUID id);

    Page<Grade> getPageGrade(SearchGradeArgument argument, Long entryId, Pageable pageable);
}
