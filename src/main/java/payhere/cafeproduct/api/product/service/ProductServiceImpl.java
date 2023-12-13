package payhere.cafeproduct.api.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payhere.cafeproduct.api.log.domain.Log;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.product.domain.Product;
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.api.product.event.dto.RequestProductUpdateDto;
import payhere.cafeproduct.api.product.event.vo.ProductDetail;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;
import payhere.cafeproduct.api.product.repository.ProductJpaRepository;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.global.dto.CommonResponse;
import payhere.cafeproduct.global.dto.Pagination;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.LogType;
import payhere.cafeproduct.global.exception.ForbiddenException;
import payhere.cafeproduct.global.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        // ** 상품 카테고리 조회
        Optional<ProductCategory> findProductCategory = productCategoryJpaRepository.findById(dto.getProductCategoryId());

        // ** 상품 카테고리 null
        if (findProductCategory.isEmpty()) {
            throw new NotFoundException("상품 카테고리 정보를 찾을 수 없습니다.");
        }

        log.info("상품 카테고리 : {}({})", findProductCategory.get().getName(), findProductCategory.get().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // ** 상품 카테고리 생성
        Product saveProduct = productJpaRepository.save(
                Product.builder()
                        .price(dto.getPrice())
                        .cost(dto.getCost())
                        .code(dto.getCode())
                        .name(dto.getName())
                        .nameConsonant(getNameConsonant(dto.getName()))
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

        log.info("상품을 생성했습니다. {}", saveProduct.toString());

        // ** 상품 카테고리 생성 로그 생성
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
        // ** 수정할 상품 조회
        ProductWithUserId data = productJpaRepository.findProductById(dto.getProductId());

        // ** 상품 조회 결과 null 처리
        if (data == null) {
            throw new NotFoundException("상품 정보를 찾을 수 없습니다.");
        }

        log.info("상품 정보 : {}", data.toString());

        // ** 수정할 상품의 유저가 로그인 유저가 아님
        if (!data.getUserId().equals(userDetailDto.getUserId())) {
            throw new ForbiddenException("상품을 수정할 권한이 없습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // ** 상품 수정
        productJpaRepository.updateProduct(
                dto.getPrice(), dto.getCost(), dto.getName(), getNameConsonant(dto.getName()), dto.getCode(), LocalDateTime.parse(dto.getExpirationDate(), formatter), dto.getProductSize(), dto.getExposeYn(), dto.getSoldOutYn(), dto.getProductCategoryId(), userDetailDto.getUserId(), dto.getProductId()
        );

        // ** 상품 수정 기록 생성
        logJpaRepository.save(
                Log.builder().logType(LogType.PRODUCT_UPDATE)
                        .log("상품을 수정합니다.")
                        .userId(userDetailDto.getUserId())
                        .logData(dto.toString())
                        .build()
        );

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품을 수정합니다.", null);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<?> deleteProduct(UserDetailDto userDetailDto, List<Long> ids) throws Exception {
        // ** 자신의 상품이 아닌 상품 조회
        boolean existProduct = productJpaRepository.existProduct(userDetailDto.getUserId(), ids);

        // ** 자신의 상품이 아닌 것이 있을 경우
        if (existProduct) {
            throw new ForbiddenException("상품을 삭제할 권한이 없습니다.");
        }

        // ** 상품 삭제
        productJpaRepository.deleteProduct(ids, userDetailDto.getUserId());

        // ** 상품 삭제 로그 저장
        logJpaRepository.save(
                Log.builder().logType(LogType.PRODUCT_DELETE)
                        .log("상품을 삭제합니다.")
                        .userId(userDetailDto.getUserId())
                        .logData(ids.toString())
                        .build()
        );

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품을 삭제합니다.", null);
    }

    @Override
    public ResponseEntity<?> findProductById(UserDetailDto userDetailDto, Long productId) throws Exception {
        // ** 상품 조회
        ProductDetail response = productJpaRepository.findProductById(userDetailDto.getUserId(), productId);

        // ** 상품 데이터 못 찾음
        if (response == null) {
            throw new NotFoundException("상품 정보를 찾을 수 없습니다.");
        }

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 정보를 조회합니다.", response);
    }

    @Override
    public ResponseEntity<?> findProductList(UserDetailDto userDetailDto, String name, Long productId, Pageable pageable) {
        // ** 상품 리스트 조회
        Page<ProductDetail> response = productJpaRepository.findProductList(userDetailDto.getUserId(), name, productId, pageable);

        // ** Response data로 Format
        Pagination data = Pagination.builder().totalPages(response.getTotalPages()).currentPage(response.getNumber()).totalItems(response.getTotalElements()).data(response.getContent()).build();

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 리스트를 조회합니다.", data);
    }

    private String getNameConsonant(String word) {
        String[] CHO = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

        return Arrays.stream(word.split("")).map(x -> {
            char cho = (char) ((x.charAt(0) - 0xAC00) / 28 / 21);
            return ((int) cho > 19 || (int) cho < 0) ? "" : CHO[(int) cho];
        }).collect(Collectors.joining(""));
    }
}
