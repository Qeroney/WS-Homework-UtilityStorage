package thewhite.homework.service.grade;

import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import thewhite.homework.aspect.annotation.LogCreateGrade;
import thewhite.homework.aspect.annotation.LogStatistics;
import thewhite.homework.model.Grade;
import thewhite.homework.model.QGrade;
import thewhite.homework.repository.GradeRepository;
import thewhite.homework.service.grade.argument.CreateGradeArgument;
import thewhite.homework.service.grade.argument.SearchGradeArgument;
import thewhite.homework.utils.QPredicates;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GradeServiceImpl implements GradeService {

    GradeRepository gradeRepository;

    QGrade qGrade = QGrade.grade;

    @Override
    @Transactional
    @LogCreateGrade
    @LogStatistics
    public Grade create(@NonNull CreateGradeArgument argument) {
        return gradeRepository.save(Grade.builder()
                                         .rating(argument.getRating())
                                         .comment(argument.getComment())
                                         .entry(argument.getEntry())
                                         .build());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @LogStatistics
    public void delete(@NonNull UUID id) {
        gradeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Grade> page(SearchGradeArgument argument, Pageable pageable) {
        Predicate predicate = buildPredicate(argument);

        return gradeRepository.findAll(predicate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public GradeStatistics getGradeStatistics() {
        Long all = gradeRepository.count();
        Double averageRating = gradeRepository.getAverageRating();
        return GradeStatistics.builder()
                              .totalGrades(all)
                              .averageRating(averageRating)
                              .build();
    }

    private Predicate buildPredicate(SearchGradeArgument argument) {
        return QPredicates.builder()
                          .add(argument.getRating(), qGrade.rating::eq)
                          .add(argument.getEntryId(), qGrade.entry.id::eq)
                          .buildAnd();
    }
}