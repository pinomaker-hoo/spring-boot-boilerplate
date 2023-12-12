package payhere.cafeproduct.api.product.repository;


import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;

public interface ProductJpaCustomRepository {
    ProductWithUserId findProductById(Long id);
}
