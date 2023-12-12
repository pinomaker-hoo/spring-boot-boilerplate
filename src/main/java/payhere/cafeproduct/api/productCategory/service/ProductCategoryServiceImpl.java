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
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.CommonResponse;
import payhere.cafeproduct.global.dto.Pagination;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.LogType;
import payhere.cafeproduct.global.exception.NotFoundException;

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

        // ** orderId를 위한 product category 개수 조회
        long productCategoryCount = productCategoryJpaRepository.findProductCategoryCountByUserId(userDetailDto.getUserId());

        ProductCategory saveProductCategory = productCategoryJpaRepository.save(ProductCategory.builder()
                .name(dto.getName())
                .exposeYn(dto.getExposeYn())
                .delYn("N")
                .orderId((int) productCategoryCount)
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
    public ResponseEntity<?> findProductCategoryList(UserDetailDto userDetailDto, Pageable pageable, Integer productCategoryId) {
        Page<ProductCategoryInfo> response = productCategoryJpaRepository.findProductCategoryList(userDetailDto.getUserId(), pageable, productCategoryId);

        Pagination data = Pagination.builder().totalPages(response.getTotalPages()).currentPage(response.getNumber()).totalItems(response.getTotalElements()).data(response.getContent()).build();

        return CommonResponse.createResponse(HttpStatus.OK.value(), "상품 카테고리 리스트를 조회합니다.", data);
    }
}
