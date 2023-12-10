package payhere.cafeproduct.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payhere.cafeproduct.api.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Integer>, UserJpaCustomRepository {
}
