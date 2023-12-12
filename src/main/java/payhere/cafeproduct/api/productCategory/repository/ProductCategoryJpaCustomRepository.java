package payhere.cafeproduct.api.productCategory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryDetail;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryWithUserId;

import java.util.List;

public interface ProductCategoryJpaCustomRepository {
    Page<ProductCategoryInfo> findProductCategoryList(Integer userId, Pageable pageable, Integer productCategoryId);

    ProductCategoryDetail findProductCategoryById(Integer userId, Integer productCategoryId);

    ProductCategoryWithUserId findProductCategoryWithUserIdById(Integer productCategoryId);

    boolean isExistProductCategory(Integer userId, List<Integer> ids);
}
