package thewhite.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>, QuerydslPredicateExecutor<Entry> {
}

