package payhere.cafeproduct.api.adminLog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payhere.cafeproduct.api.adminLog.domain.AdminLog;

public interface AdminLogJpaRepository extends JpaRepository<AdminLog, Long>, AdminLogJpaCustomRepository {
}
