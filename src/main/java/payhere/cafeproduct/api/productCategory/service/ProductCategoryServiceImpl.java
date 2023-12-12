package payhere.cafeproduct.api.productCategory.service;

import lombok.RequiredArgsConstructor;
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
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryJpaRepository productCategoryJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LogJpaRepository logJpaRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<?> saveProductCategory(UserDetailDto userDetailDto, RequestProductCategorySaveDto dto) throws Exception {
        Optional<User> loginUser = userJpaRepository.findById(userDetailDto.getUserId());

        if (loginUser.isEmpty()) {
            throw new NotFoundException("로그인한 유저 정보를 찾을 수 없습니다.");
        }

        ProductCategory saveProductCategory = productCategoryJpaRepository.save(ProductCategory.builder()
                .name(dto.getName())
                .exposeYn(dto.getExposeYn())
                .delYn("N")
                .user(loginUser.get())
                .createdId(userDetailDto.getUserId())
                .modifiedId(userDetailDto.getUserId())
                .build());

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
        Page<ProductCategoryInfo> response = productCategoryJpaRepository.findProductCategoryList(userDetailDto.getUserId(), pageable, productCategoryId);

        Pagination data = Pagination.builder().totalPages(response.getTotalPages()).currentPage(response.getNumber()).totalItems(response.getTotalElements()).data(response.getContent()).build();

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리 리스트를 조회합니다.", data);
    }

    @Override
    public ResponseEntity<?> findProductCategoryById(UserDetailDto userDetailDto, Integer productCategoryId) throws Exception {
        ProductCategoryDetail response = productCategoryJpaRepository.findProductCategoryById(userDetailDto.getUserId(), productCategoryId);

        if (response == null) {
            throw new NotFoundException("상품 카테고리 정보를 찾을 수 없습니다.");
        }

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리 정보를 조회합니다.", response);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<?> updateProductCategory(UserDetailDto userDetailDto, RequestProductCategoryUpdateDto dto) throws Exception {
        ProductCategoryWithUserId data = productCategoryJpaRepository.findProductCategoryWithUserIdById(dto.getProductCategoryId());

        if (data == null) {
            throw new NotFoundException("상품 카테고리 정보를 찾을 수 없습니다.");
        }

        if (!data.getUserId().equals(userDetailDto.getUserId())) {
            throw new ForbiddenException("해당 카테고리를 수정할 권한이 없습니다.");
        }

        productCategoryJpaRepository.updateProductCategory(
                dto.getName(), dto.getExposeYn(), dto.getProductCategoryId()
        );

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
        boolean existProductCategory = productCategoryJpaRepository.isExistProductCategory(userDetailDto.getUserId(), ids);

        if (existProductCategory) {
            throw new ForbiddenException("해당 카테고리를 삭제할 권한이 없습니다.");
        }

        productCategoryJpaRepository.deleteProductCategory(ids, userDetailDto.getUserId());

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
