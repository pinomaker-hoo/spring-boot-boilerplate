package payhere.cafeproduct.api.product.service;

import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.api.product.event.dto.RequestProductUpdateDto;
import payhere.cafeproduct.global.dto.UserDetailDto;

public interface ProductService {
    ResponseEntity<?> saveProduct(UserDetailDto userDetailDto, RequestProductSaveDto dto) throws Exception;

    ResponseEntity<?> updateProduct(UserDetailDto userDetailDto, RequestProductUpdateDto dto) throws Exception;
}
