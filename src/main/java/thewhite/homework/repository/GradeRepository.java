package thewhite.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Grade;

import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, UUID>, QuerydslPredicateExecutor<Grade> {
}
