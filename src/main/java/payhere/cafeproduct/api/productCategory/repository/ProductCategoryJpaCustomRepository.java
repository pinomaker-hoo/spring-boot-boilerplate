package payhere.cafeproduct.api.productCategory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;

public interface ProductCategoryJpaCustomRepository {
    long findProductCategoryCountByUserId(Integer userId);

    Page<ProductCategoryInfo> findProductCategoryList(Integer userId, Pageable pageable, Integer productCategoryId);
}
