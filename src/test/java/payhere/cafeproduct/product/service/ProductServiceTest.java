package payhere.cafeproduct.product.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.product.repository.ProductJpaRepository;
import payhere.cafeproduct.api.product.service.ProductServiceImpl;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategoryUpdateDto;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryDetail;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryWithUserId;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.api.productCategory.service.ProductCategoryServiceImpl;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.UserRole;
import payhere.cafeproduct.global.exception.ForbiddenException;
import payhere.cafeproduct.global.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Product test")
public class ProductServiceTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private ProductCategoryJpaRepository productCategoryJpaRepository;

    @Mock
    private ProductJpaRepository productJpaRepository;

    @Mock
    private LogJpaRepository logJpaRepository;


    @Test
    @DisplayName("상품 등록 - 상품 카테고리를 찾을 수 없습니다.")
    public void 상품_등록_카테고리를_찾을_수_없습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 등록 - 상품 등록에 성공했습니다.")
    public void 상품_등록_성공_했습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 리스트 조회 - 상품 리스트 조회에 성공했습니다.")
    public void 상품_리스트_조회_성공했습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 조회 - 상품 정보를 찾을 수 없습니다.")
    public void 상품_조회_데이터를_찾을_수_없습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 조회 - 상품 정보 조회에 성공했습니다.")
    public void 상품_조회_성공_했습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 수정 - 상품 정보를 찾을 수 없습니다.")
    public void 상품_수정_데이터를_찾을_수_없습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 수정 - 수정 권한이 없습니다")
    public void 상품_수정_권한이_없습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 수정 - 상품 수정에 성공했습니다")
    public void 상품_수정_성공_했습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 삭제 - 상품 삭제 권한이 없습니다")
    public void 상품_삭제_권한이_없습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 삭제 - 상품 삭제에 성공했습니다")
    public void 상품_삭제_성공_했습니다() throws Exception {
    }
}
