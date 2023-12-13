package payhere.cafeproduct.api.productCategory.service;

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
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategoryUpdateDto;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryDetail;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryWithUserId;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.CommonResponse;
import payhere.cafeproduct.global.dto.Pagination;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.LogType;
import payhere.cafeproduct.global.exception.ForbiddenException;
import payhere.cafeproduct.global.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryJpaRepository productCategoryJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LogJpaRepository logJpaRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<?> saveProductCategory(UserDetailDto userDetailDto, RequestProductCategorySaveDto dto) throws Exception {
        // ** 유저 조회
        Optional<User> loginUser = userJpaRepository.findById(userDetailDto.getUserId());

        // ** 유저 조회 실패
        if (loginUser.isEmpty()) {
            throw new NotFoundException("로그인한 유저 정보를 찾을 수 없습니다.");
        }

        log.info("user : {}({})", loginUser.get().getPhoneNumber(), loginUser.get().getId());

        // ** 상품 카테고리 저장
        ProductCategory saveProductCategory = productCategoryJpaRepository.save(ProductCategory.builder()
                .name(dto.getName())
                .exposeYn(dto.getExposeYn())
                .delYn("N")
                .user(loginUser.get())
                .createdId(userDetailDto.getUserId())
                .modifiedId(userDetailDto.getUserId())
                .build());

        log.info("생성한 상품 카테고리 : {}", saveProductCategory.toString());

        // ** 상품 카테고리 저장 로그 저장
        logJpaRepository.save(
                Log.builder().logType(LogType.PRODUCT_CATEGORY_ADD)
                        .log("상품 카테고리를 생성합니다.")
                        .userId(userDetailDto.getUserId())
                        .logData(saveProductCategory.toString())
                        .build()
        );

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리를 생성합니다.", null);
    }

    @Override
    public ResponseEntity<?> findProductCategoryList(UserDetailDto userDetailDto, Pageable pageable, Integer productCategoryId) throws Exception {
        // ** 상품 카테고리 리스트 조회
        Page<ProductCategoryInfo> response = productCategoryJpaRepository.findProductCategoryList(userDetailDto.getUserId(), pageable, productCategoryId);

        // ** Response data로 Format
        Pagination data = Pagination.builder().totalPages(response.getTotalPages()).currentPage(response.getNumber()).totalItems(response.getTotalElements()).data(response.getContent()).build();

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리 리스트를 조회합니다.", data);
    }

    @Override
    public ResponseEntity<?> findProductCategoryById(UserDetailDto userDetailDto, Integer productCategoryId) throws Exception {
        // ** 상품 카테고리 조회
        ProductCategoryDetail response = productCategoryJpaRepository.findProductCategoryById(userDetailDto.getUserId(), productCategoryId);

        // ** 상품 카테고리 조회 실패
        if (response == null) {
            throw new NotFoundException("상품 카테고리 정보를 찾을 수 없습니다.");
        }

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리 정보를 조회합니다.", response);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<?> updateProductCategory(UserDetailDto userDetailDto, RequestProductCategoryUpdateDto dto) throws Exception {
        // ** 수정할 상품 카테고리 조회
        ProductCategoryWithUserId data = productCategoryJpaRepository.findProductCategoryWithUserIdById(dto.getProductCategoryId());

        // ** 조회 실패
        if (data == null) {
            throw new NotFoundException("상품 카테고리 정보를 찾을 수 없습니다.");
        }

        log.info("수정 상품 카테고리 ID : {}, 수정 카테고리의 유저 ID : {}", data.getId(), data.getUserId());

        // ** 조회한 데이터의 유저와 로그인 유저가 다름
        if (!data.getUserId().equals(userDetailDto.getUserId())) {
            throw new ForbiddenException("해당 카테고리를 수정할 권한이 없습니다.");
        }

        // ** 상품 카테고리 수정
        productCategoryJpaRepository.updateProductCategory(
                dto.getName(), dto.getExposeYn(), dto.getProductCategoryId()
        );

        // ** 상품 카테고리 수정 로그 저장
        logJpaRepository.save(
                Log.builder().logType(LogType.PRODUCT_CATEGORY_UPDATE)
                        .log("상품 카테고리를 수정합니다.")
                        .userId(userDetailDto.getUserId())
                        .logData(dto.toString())
                        .build()
        );

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리 정보를 수정합니다.", null);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<?> deleteProductCategory(UserDetailDto userDetailDto, List<Integer> ids) throws Exception {
        // ** 자신의 상품 카테고리가 아닌 상품 조회
        boolean existProductCategory = productCategoryJpaRepository.isExistProductCategory(userDetailDto.getUserId(), ids);

        // ** 자신의 상품 카테고리가 아닌 것이 있을 경우
        if (existProductCategory) {
            throw new ForbiddenException("해당 카테고리를 삭제할 권한이 없습니다.");
        }

        // ** 카테고리 삭제
        productCategoryJpaRepository.deleteProductCategory(ids, userDetailDto.getUserId());

        // ** 카테고리 삭제 기능 조회
        logJpaRepository.save(
                Log.builder().logType(LogType.PRODUCT_CATEGORY_DELETE)
                        .log("상품 카테고리를 삭제합니다.")
                        .userId(userDetailDto.getUserId())
                        .logData(ids.toString())
                        .build()
        );

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리를 삭제합니다.", null);
    }
}
