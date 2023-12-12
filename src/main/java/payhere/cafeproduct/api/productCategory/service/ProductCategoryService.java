package payhere.cafeproduct.api.productCategory.service;

import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.global.dto.UserDetailDto;

public interface ProductCategoryService {
    ResponseEntity<?> saveProductCategory(UserDetailDto userDetailDto, RequestProductCategorySaveDto dto) throws Exception;
}
