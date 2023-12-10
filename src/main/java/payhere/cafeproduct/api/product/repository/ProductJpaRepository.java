package payhere.cafeproduct.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payhere.cafeproduct.api.product.domain.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductJpaCustomRepository {
}
