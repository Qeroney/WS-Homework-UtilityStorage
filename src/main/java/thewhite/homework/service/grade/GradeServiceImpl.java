package thewhite.homework.service.grade;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import thewhite.homework.exception.NotFoundException;
import thewhite.homework.model.Grade;
import thewhite.homework.repository.grade.GradeRepository;
import thewhite.homework.service.grade.argument.CreateGradeArgument;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GradeServiceImpl implements GradeService {

    GradeRepository gradeRepository;

    @Override
    public Grade create(CreateGradeArgument argument) {
        return gradeRepository.save(Grade.builder()
                                         .entryId(argument.getEntryId())
                                         .rating(argument.getRating())
                                         .comment(argument.getComment())
                                         .build());
    }

    @Override
    public void delete(UUID id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public List<Grade> getAllExisting(Long entryId) {
        return Optional.ofNullable(gradeRepository.findAllById(entryId))
                       .orElseThrow(() -> new NotFoundException("Оценка не найдена"));
    }
}
