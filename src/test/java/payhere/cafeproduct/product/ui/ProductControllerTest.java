package payhere.cafeproduct.product.ui;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.api.product.event.dto.RequestProductUpdateDto;
import payhere.cafeproduct.api.product.event.vo.ProductDetail;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;
import payhere.cafeproduct.api.product.repository.ProductJpaRepository;
import payhere.cafeproduct.api.product.service.ProductServiceImpl;
import payhere.cafeproduct.api.product.ui.ProductController;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.ProductSize;
import payhere.cafeproduct.global.enums.UserRole;
import payhere.cafeproduct.global.jwt.JwtTokenExtractor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Product Controller test")
public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private ProductJpaRepository productJpaRepository;

    @Mock
    private JwtTokenExtractor jwtTokenExtractor;

    @Mock
    private ProductCategoryJpaRepository productCategoryJpaRepository;

    @Mock
    private LogJpaRepository logJpaRepository;

    @Spy
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void initEnvironment() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @DisplayName("상품 생성 API를 사용할 수 있습니다.")
    public void 상품_생성_성공했습니다() throws Exception {
        // Given
        RequestProductSaveDto request = RequestProductSaveDto.builder()
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

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productCategoryJpaRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(productJpaRepository.save(any())).thenReturn(productCategory);

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 리스트 조회 API를 사용할 수 있습니다.")
    public void 상품_리스트_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productJpaRepository.findProductList(anyInt(), anyString(), anyLong(), any()))
                .thenReturn(new PageImpl<>(List.of(
                        new ProductDetail(1L, 30000, 10000, "ICE TEA", "ABCD_EF@_123", LocalDateTime.now(),
                                ProductSize.LARGE, "Y", "Y", "TEA", LocalDateTime.now()),
                        new ProductDetail(2L, 30000, 10000, "ICE TEA", "ABCD_EF@_123", LocalDateTime.now(),
                                ProductSize.LARGE, "Y", "Y", "TEA", LocalDateTime.now())
                )));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/product")
                        .param("productId", "0")
                        .param("page", "0")
                        .param("size", "10")
                        .param("name", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 조회 API를 사용할 수 있습니다.")
    public void 상품_데이터_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productJpaRepository.findProductById(anyInt(), anyLong()))
                .thenReturn(new ProductDetail(1L, 30000, 10000, "ICE TEA", "ABCD_EF@_123", LocalDateTime.now(),
                        ProductSize.LARGE, "Y", "Y", "TEA", LocalDateTime.now()));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 API를 사용할 수 있습니다.")
    public void 상품_카테고리_수정_성공했습니다() throws Exception {
        // Given
        RequestProductUpdateDto request = RequestProductUpdateDto.builder()
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
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productJpaRepository.findProductById(anyLong())).thenReturn(new ProductWithUserId(1L, 1));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/product")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 삭제 API를 사용할 수 있습니다.")
    public void 상품_삭제_성공했습니다() throws Exception {
        // Given
        List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2, 3));
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productJpaRepository.existProduct(anyInt(), anyList())).thenReturn(false);

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/product")
                        .param("id", ids.stream().map(Object::toString).toArray(String[]::new))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    // ** Test UserDetailDto 생성
    private UserDetailDto generateUserDetailDto() {
        return UserDetailDto.builder().userId(1).role(UserRole.ROLE_MEMBER).build();
    }


    // ** Test Product Category 생성
    private ProductCategory generateProductCategory() {
        return ProductCategory.builder().name("tea").exposeYn("Y").build();
    }
}
