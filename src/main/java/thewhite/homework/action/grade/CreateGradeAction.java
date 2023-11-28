package thewhite.homework.action.grade;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import thewhite.homework.model.Entry;
import thewhite.homework.model.Grade;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.grade.GradeService;
import thewhite.homework.service.grade.argument.CreateGradeArgument;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
@Validated
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CreateGradeAction {

    EntryService entryService;

    GradeService gradeService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Grade execute(@Valid CreateGradeActionArgument argument) {
        Entry existing = entryService.getExisting(argument.getEntryId());

        return gradeService.create(CreateGradeArgument.builder()
                                                      .rating(argument.getRating())
                                                      .comment(argument.getComment())
                                                      .entry(existing)
                                                      .build());
    }
}
