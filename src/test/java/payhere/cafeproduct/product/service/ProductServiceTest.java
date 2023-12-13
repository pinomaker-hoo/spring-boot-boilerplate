package payhere.cafeproduct.product.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.product.domain.Product;
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.api.product.event.dto.RequestProductUpdateDto;
import payhere.cafeproduct.api.product.event.vo.ProductDetail;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;
import payhere.cafeproduct.api.product.repository.ProductJpaRepository;
import payhere.cafeproduct.api.product.service.ProductServiceImpl;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.ProductSize;
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
        // Given
        RequestProductSaveDto dto = RequestProductSaveDto.builder()
                .name("tea")
                .price(30000)
                .code("ABCD_EF@_123")
                .cost(10000)
                .productCategoryId(1)
                .productSize(ProductSize.LARGE)
                .exposeYn("Y")
                .expirationDate("2024-12-31 12:00:00")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(productCategoryJpaRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.saveProduct(userDetailDto, dto);
        });

        // Then
        assertEquals("상품 카테고리 정보를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 등록 - 상품 등록에 성공했습니다.")
    public void 상품_등록_성공_했습니다() throws Exception {
        // Given
        RequestProductSaveDto dto = RequestProductSaveDto.builder()
                .name("tea")
                .price(30000)
                .code("ABCD_EF@_123")
                .cost(10000)
                .productCategoryId(1)
                .productSize(ProductSize.LARGE)
                .exposeYn("Y")
                .expirationDate("2024-12-31 12:00:00")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();
        ProductCategory productCategory = generateProductCategory();
        Product product = generateProduct(productCategory);

        // Mock
        when(productCategoryJpaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(productCategory));
        when(productJpaRepository.save(any())).thenReturn(product);

        // When
        ResponseEntity<?> response = productService.saveProduct(userDetailDto, dto);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("상품 리스트 조회 - 상품 리스트 조회에 성공했습니다.")
    public void 상품_리스트_조회_성공했습니다() throws Exception {
    }

    @Test
    @DisplayName("상품 조회 - 상품 정보를 찾을 수 없습니다.")
    public void 상품_조회_데이터를_찾을_수_없습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();
        Long productId = 1L;

        // Mock
        when(productJpaRepository.findProductById(anyInt(), anyLong()))
                .thenReturn(null);

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.findProductById(userDetailDto, productId);
        });

        // Then
        assertEquals("상품 정보를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 조회 - 상품 정보 조회에 성공했습니다.")
    public void 상품_조회_성공_했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();
        Long productId = 1L;

        // Mock
        when(productJpaRepository.findProductById(anyInt(), anyLong()))
                .thenReturn(new ProductDetail(1L, 30000, 10000, "ICE TEA", "ABCD_EF@_123", LocalDateTime.now(),
                        ProductSize.LARGE, "Y", "Y", "TEA", LocalDateTime.now()));

        // When
        ResponseEntity<?> response = productService.findProductById(userDetailDto, productId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("상품 수정 - 상품 정보를 찾을 수 없습니다.")
    public void 상품_수정_데이터를_찾을_수_없습니다() throws Exception {
        // Given
        RequestProductUpdateDto dto = RequestProductUpdateDto.builder()
                .productId(1L)
                .productCategoryId(1)
                .price(30000)
                .cost(10000)
                .name("tea")
                .code("ABCD_EF@_123")
                .expirationDate("2024-12-31 12:00:00")
                .productSize(ProductSize.LARGE)
                .exposeYn("Y")
                .soldOutYn("N")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(productJpaRepository.findProductById(anyLong())).thenReturn(null);

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(userDetailDto, dto);
        });

        // Then
        assertEquals("상품 정보를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 수정 - 수정 권한이 없습니다")
    public void 상품_수정_권한이_없습니다() throws Exception {
        // Given
        RequestProductUpdateDto dto = RequestProductUpdateDto.builder()
                .productId(1L)
                .productCategoryId(1)
                .price(30000)
                .cost(10000)
                .name("tea")
                .code("ABCD_EF@_123")
                .expirationDate("2024-12-31 12:00:00")
                .productSize(ProductSize.LARGE)
                .exposeYn("Y")
                .soldOutYn("N")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(productJpaRepository.findProductById(anyLong())).thenReturn(new ProductWithUserId(1L, 3));

        // When
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            productService.updateProduct(userDetailDto, dto);
        });

        // Then
        assertEquals("상품을 수정할 권한이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 수정 - 상품 수정에 성공했습니다")
    public void 상품_수정_성공_했습니다() throws Exception {
        // Given
        RequestProductUpdateDto dto = RequestProductUpdateDto.builder()
                .productId(1L)
                .productCategoryId(1)
                .price(30000)
                .cost(10000)
                .name("tea")
                .code("ABCD_EF@_123")
                .expirationDate("2024-12-31 12:00:00")
                .productSize(ProductSize.LARGE)
                .exposeYn("Y")
                .soldOutYn("N")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();
        ProductCategory productCategory = generateProductCategory();
        Product product = generateProduct(productCategory);

        // Mock
        when(productJpaRepository.findProductById(anyLong())).thenReturn(new ProductWithUserId(1L, 1));


        // When
        ResponseEntity<?> response = productService.updateProduct(userDetailDto, dto);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("상품 삭제 - 상품 삭제 권한이 없습니다")
    public void 상품_삭제_권한이_없습니다() throws Exception {
        // Given
        List<Long> ids = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(productJpaRepository.existProduct(anyInt(), anyList())).thenReturn(true);

        // When
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            productService.deleteProduct(userDetailDto, ids);
        });

        // Then
        assertEquals("상품을 삭제할 권한이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 삭제 - 상품 삭제에 성공했습니다")
    public void 상품_삭제_성공_했습니다() throws Exception {
        // Given
        List<Long> ids = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(productCategoryJpaRepository.isExistProductCategory(anyInt(), anyList())).thenReturn(false);

        // When
        ResponseEntity<?> response = productService.deleteProduct(userDetailDto, ids);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    // ** Test UserDetailDto 생성
    private UserDetailDto generateUserDetailDto() {
        return UserDetailDto.builder().userId(1).role(UserRole.ROLE_MEMBER).build();
    }


    // ** Test Product Category 생성
    private ProductCategory generateProductCategory() {
        return ProductCategory.builder().id(1).name("tea").exposeYn("Y").build();
    }

    // ** Test Product 생성
    private Product generateProduct(ProductCategory productCategory) {
        return Product.builder().id(1L).productCategory(productCategory)
                .name("아메리카노").price(30000).cost(10000).code("ABCD_EF@_123")
                .exposeYn("N").size(ProductSize.LARGE).expirationDate(LocalDateTime.now())
                .build();
    }
}