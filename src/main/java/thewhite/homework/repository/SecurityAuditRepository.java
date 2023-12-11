package thewhite.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import thewhite.homework.model.SecurityAudit;

@Repository
public interface SecurityAuditRepository extends JpaRepository<SecurityAudit, Long>, QuerydslPredicateExecutor<SecurityAudit> {
}
