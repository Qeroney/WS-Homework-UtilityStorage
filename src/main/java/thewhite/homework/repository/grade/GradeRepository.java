package thewhite.homework.repository.grade;

import thewhite.homework.model.Grade;

import java.util.List;
import java.util.UUID;

public interface GradeRepository {

    Grade save(Grade grade);

    List<Grade> findAllById(Long entryId);

    void deleteById(UUID id);
}
