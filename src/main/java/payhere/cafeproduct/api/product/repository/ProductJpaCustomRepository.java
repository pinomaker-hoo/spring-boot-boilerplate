package payhere.cafeproduct.api.product.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import payhere.cafeproduct.api.product.event.vo.ProductDetail;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;

import java.util.List;

public interface ProductJpaCustomRepository {
    ProductWithUserId findProductById(Long id);

    boolean existProduct(Integer userId, List<Long> ids);

    ProductDetail findProductById(Integer userId, Long productId);

    Page<ProductDetail> findProductList(Integer userId, String name, Long productId, Pageable pageable);
}
