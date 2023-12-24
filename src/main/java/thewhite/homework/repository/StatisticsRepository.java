package thewhite.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.Statistics;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Statistics findFirstByOrderById();
}