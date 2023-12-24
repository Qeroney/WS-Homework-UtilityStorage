package thewhite.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>, QuerydslPredicateExecutor<Entry> {

    @Query("SELECT COUNT(e) FROM Entry e WHERE e.grades IS EMPTY")
    Long getEntriesWithoutGrades();

    @Query("SELECT COUNT(e) FROM Entry e WHERE NOT EXISTS (SELECT g FROM e.grades g WHERE g.rating < 4)")
    Long getNoLessThanFourEntries();

    @Query("SELECT COUNT(e) FROM Entry e WHERE (SELECT AVG(g.rating) FROM e.grades g) >= 4")
    Long getAboveFourEntries();

    @Query("SELECT COUNT(e) FROM Entry e WHERE (SELECT AVG(g.rating) FROM Grade g WHERE g.entry = e) = 5")
    Long entriesWithAverageGradeEqualsFive();
}