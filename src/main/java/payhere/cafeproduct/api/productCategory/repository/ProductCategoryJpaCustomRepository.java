package payhere.cafeproduct.api.productCategory.repository;

public interface ProductCategoryJpaCustomRepository {
    long findProductCategoryCountByUserId(Integer userId);
}
