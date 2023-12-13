package payhere.cafeproduct.api.product.repository;


import payhere.cafeproduct.api.product.event.vo.ProductDetail;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;

import java.util.List;

public interface ProductJpaCustomRepository {
    ProductWithUserId findProductById(Long id);

    boolean existProduct(Integer userId, List<Long> ids);

    ProductDetail findProductById(Integer userId, Long productId);
}
