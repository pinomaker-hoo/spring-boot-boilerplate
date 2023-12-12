package payhere.cafeproduct.api.productCategory.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategoryUpdateDto;
import payhere.cafeproduct.global.dto.UserDetailDto;

public interface ProductCategoryService {
    ResponseEntity<?> saveProductCategory(UserDetailDto userDetailDto, RequestProductCategorySaveDto dto) throws Exception;

    ResponseEntity<?> findProductCategoryList(UserDetailDto userDetailDto, Pageable pageable, Integer productCategoryId) throws Exception;

    ResponseEntity<?> findProductCategoryById(UserDetailDto userDetailDto, Integer productCategoryId) throws Exception;

    ResponseEntity<?> updateProductCategory(UserDetailDto userDetailDto, RequestProductCategoryUpdateDto dto) throws Exception;
}
