package payhere.cafeproduct.api.productCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategory, Integer>, ProductCategoryJpaCustomRepository {
}
