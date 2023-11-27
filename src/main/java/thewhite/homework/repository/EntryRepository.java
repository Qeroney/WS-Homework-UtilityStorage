package thewhite.homework.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>, QuerydslPredicateExecutor<Entry> {

    Page<Entry> findAll(Predicate predicate, Pageable pageable);
}

