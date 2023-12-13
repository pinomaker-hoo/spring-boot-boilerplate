package payhere.cafeproduct.api.product.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.api.product.event.dto.RequestProductUpdateDto;
import payhere.cafeproduct.global.dto.UserDetailDto;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> saveProduct(UserDetailDto userDetailDto, RequestProductSaveDto dto) throws Exception;

    ResponseEntity<?> updateProduct(UserDetailDto userDetailDto, RequestProductUpdateDto dto) throws Exception;

    ResponseEntity<?> deleteProduct(UserDetailDto userDetailDto, List<Long> ids) throws Exception;

    ResponseEntity<?> findProductById(UserDetailDto userDetailDto, Long productId) throws Exception;

    ResponseEntity<?> findProductList(UserDetailDto userDetailDto, String name, Long productId, Pageable pageable);
}
