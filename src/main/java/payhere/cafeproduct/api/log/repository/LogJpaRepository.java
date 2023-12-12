package payhere.cafeproduct.api.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payhere.cafeproduct.api.log.domain.Log;

public interface LogJpaRepository extends JpaRepository<Log, Long>, LogJpaCustomRepository {
}
