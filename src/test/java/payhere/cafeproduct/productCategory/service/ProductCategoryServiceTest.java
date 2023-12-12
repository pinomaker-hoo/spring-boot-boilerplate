package payhere.cafeproduct.productCategory.service;


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
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryDetail;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.api.productCategory.service.ProductCategoryServiceImpl;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.UserRole;
import payhere.cafeproduct.global.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Product Category test")
public class ProductCategoryServiceTest {
    @InjectMocks
    private ProductCategoryServiceImpl productCategoryService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private ProductCategoryJpaRepository productCategoryJpaRepository;

    @Mock
    private LogJpaRepository logJpaRepository;

    @Test
    @DisplayName("상품 카테고리 생성 - 로그인한 유저 정보를 찾을 수 없습니다.")
    public void 상품_카테고리_생성_로그인한_유저_정보를_찾을_수_없습니다() throws Exception {
        // Given
        RequestProductCategorySaveDto dto = RequestProductCategorySaveDto.builder()
                .name("tea")
                .exposeYn("Y")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(userJpaRepository.findById(userDetailDto.getUserId())).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productCategoryService.saveProductCategory(userDetailDto, dto);
        });

        // Then
        assertEquals("로그인한 유저 정보를 찾을 수 없습니다.", exception.getMessage());

    }

    @Test
    @DisplayName("상품 카테고리 생성 - 상품 카테고리 생성에 성공했습니다.")
    public void 상품_카테고리_생성_성공했습니다() throws Exception {
        // Given
        RequestProductCategorySaveDto dto = RequestProductCategorySaveDto.builder()
                .name("tea")
                .exposeYn("Y")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();
        User loginUser = generateUser();
        ProductCategory productCategory = generateProductCategory();

        // Mock
        when(userJpaRepository.findById(userDetailDto.getUserId())).thenReturn(Optional.ofNullable(loginUser));
        when(productCategoryJpaRepository.save(any())).thenReturn(productCategory);

        // When
        ResponseEntity<?> response = productCategoryService.saveProductCategory(userDetailDto, dto);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("상품 카테고리 리스트 조회 - 상품 카테고리 리스트 조회에 성공했습니다.")
    public void 상품_카테고리_리스트_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();
        Pageable pageable = PageRequest.of(0, 10);
        Integer productCategoryId = 0;

        // Mock
        when(productCategoryJpaRepository.findProductCategoryList(anyInt(), any(), anyInt()))
                .thenReturn(new PageImpl<>(List.of(
                        new ProductCategoryInfo(1, "coffee"),
                        new ProductCategoryInfo(2, "tea")
                )));

        // When
        ResponseEntity<?> response = productCategoryService.findProductCategoryList(userDetailDto, pageable, productCategoryId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("상품 카테고리 조회 - 상품 카테고리 정보를 찾을 수 없습니다.")
    public void 상품_카테고리_데이터_조회_데이터를_찾을_수_없습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();
        Integer productCategoryId = 1;

        // Mock
        when(productCategoryJpaRepository.findProductCategoryById(anyInt(), anyInt())).thenReturn(null);

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productCategoryService.findProductCategoryById(userDetailDto, productCategoryId);
        });

        // Then
        assertEquals("상품 카테고리 정보를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 카테고리 조회 - ID 기반의 상품 카테고리 조회에 성공했습니다.")
    public void 상품_카테고리_데이터_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();
        Integer productCategoryId = 1;

        // Mock
        when(productCategoryJpaRepository.findProductCategoryById(anyInt(), anyInt()))
                .thenReturn(new ProductCategoryDetail(1, "coffee", "Y", LocalDateTime.now()));

        // When
        ResponseEntity<?> response = productCategoryService.findProductCategoryById(userDetailDto, productCategoryId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("상품 카테고리 수정 - 상품 카테고리 정보를 찾을 수 없습니다.")
    public void 상품_카테고리_수정_데이터를_찾을_수_없습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
    }

    @Test
    @DisplayName("상품 카테고리 수정 - 해당 상품 수정 권한이 없습니다.")
    public void 상품_카테고리_수정_권한이_없습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
    }

    @Test
    @DisplayName("상품 카테고리 수정 - 상품 카테고리 수정에 성공했습니다.")
    public void 상품_카테고리_수정_성공했습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
    }

    @Test
    @DisplayName("상품 카테고리 삭제 - 상품 카테고리 삭제에 성공했습니다.")
    public void 상품_카테고리_삭제_성공했습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
    }

    // ** Test UserDetailDto 생성
    private UserDetailDto generateUserDetailDto() {
        return UserDetailDto.builder().userId(1).role(UserRole.ROLE_MEMBER).build();
    }

    // ** Test User 생성
    private User generateUser() {
        return User.builder().id(0).phoneNumber("010-1234-5678").password("1234").build();
    }


    // ** Test Product Category 생성
    private ProductCategory generateProductCategory() {
        return ProductCategory.builder().name("tea").exposeYn("Y").build();
    }
}
