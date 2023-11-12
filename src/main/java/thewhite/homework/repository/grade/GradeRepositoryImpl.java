package thewhite.homework.repository.grade;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Grade;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GradeRepositoryImpl implements GradeRepository {
    Map<UUID, Grade> gradeMap = new HashMap<>();

    @Override
    public Grade save(Grade grade) {
        gradeMap.put(grade.getId(), grade);
        return grade;
    }

    @Override
    public List<Grade> findAllById(Long entryId) {
        return gradeMap.values().stream()
                     .filter(grade -> grade.getEntryId().equals(entryId))
                     .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        gradeMap.remove(id);
    }
}
