package payhere.cafeproduct.api.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payhere.cafeproduct.api.log.domain.Log;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.product.domain.Product;
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.api.product.event.dto.RequestProductUpdateDto;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;
import payhere.cafeproduct.api.product.repository.ProductJpaRepository;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.global.dto.CommonResponse;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.LogType;
import payhere.cafeproduct.global.exception.ForbiddenException;
import payhere.cafeproduct.global.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductJpaRepository productJpaRepository;
    private final ProductCategoryJpaRepository productCategoryJpaRepository;
    private final LogJpaRepository logJpaRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<?> saveProduct(UserDetailDto userDetailDto, RequestProductSaveDto dto) throws Exception {
        Optional<ProductCategory> findProductCategory = productCategoryJpaRepository.findById(dto.getProductCategoryId());

        if (findProductCategory.isEmpty()) {
            throw new NotFoundException("상품 카테고리 정보를 찾을 수 없습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Product saveProduct = productJpaRepository.save(
                Product.builder()
                        .price(dto.getPrice())
                        .cost(dto.getCost())
                        .code(dto.getCode())
                        .name(dto.getName())
                        .expirationDate(LocalDateTime.parse(dto.getExpirationDate(), formatter))
                        .exposeYn(dto.getExposeYn())
                        .size(dto.getProductSize())
                        .productCategory(findProductCategory.get())
                        .soldOutYn("N")
                        .delYn("N")
                        .createdId(userDetailDto.getUserId())
                        .modifiedId(userDetailDto.getUserId())
                        .build()
        );

        logJpaRepository.save(
                Log.builder().logType(LogType.PRODUCT_ADD)
                        .log("상품을 생성합니다.")
                        .userId(userDetailDto.getUserId())
                        .logData(saveProduct.toString())
                        .build()
        );


        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품을 생성합니다.", null);
    }

    @Override
    public ResponseEntity<?> updateProduct(UserDetailDto userDetailDto, RequestProductUpdateDto dto) throws Exception {
        ProductWithUserId data = productJpaRepository.findProductById(dto.getProductId());

        log.info("DATA : {}", data);

        if (data == null) {
            throw new NotFoundException("상품 정보를 찾을 수 없습니다.");
        }

        if (!data.getUserId().equals(userDetailDto.getUserId())) {
            throw new ForbiddenException("상품을 수정할 권한이 없습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        productJpaRepository.updateProduct(
                dto.getPrice(), dto.getCost(), dto.getName(), dto.getCode(), LocalDateTime.parse(dto.getExpirationDate(), formatter), dto.getProductSize(), dto.getExposeYn(), dto.getSoldOutYn(), dto.getProductCategoryId(), userDetailDto.getUserId(), dto.getProductId()
        );

        logJpaRepository.save(
                Log.builder().logType(LogType.PRODUCT_UPDATE)
                        .log("상품을 수정합니다.")
                        .userId(userDetailDto.getUserId())
                        .logData(dto.toString())
                        .build()
        );

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품을 수정합니다.", null);
    }
}
