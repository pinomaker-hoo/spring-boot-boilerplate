package payhere.cafeproduct.api.product.service;

import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.global.dto.UserDetailDto;

public interface ProductService {
    ResponseEntity<?> saveProduct(UserDetailDto userDetailDto, RequestProductSaveDto dto) throws Exception;
}
