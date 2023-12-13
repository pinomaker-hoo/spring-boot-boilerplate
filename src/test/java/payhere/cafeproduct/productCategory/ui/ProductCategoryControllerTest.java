package payhere.cafeproduct.productCategory.ui;


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
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategoryUpdateDto;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryDetail;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryWithUserId;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.api.productCategory.service.ProductCategoryServiceImpl;
import payhere.cafeproduct.api.productCategory.ui.ProductCategoryController;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.UserDetailDto;
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
@DisplayName("Product Category test")
public class ProductCategoryControllerTest {
    @InjectMocks
    private ProductCategoryController productCategoryController;

    @Mock
    private ProductCategoryServiceImpl productCategoryService;

    @Mock
    private UserJpaRepository userJpaRepository;

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
        mockMvc = MockMvcBuilders.standaloneSetup(productCategoryController).build();
    }

    @Test
    @DisplayName("상품 카테고리 생성 API를 사용 가능합니다.")
    public void 상품_카테고리_생성_성공했습니다() throws Exception {
        // Given
        RequestProductCategorySaveDto request = RequestProductCategorySaveDto.builder()
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
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/product/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 카테고리 리스트 조회 API를 사용 가능합니다.")
    public void 상품_카테고리_리스트_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productCategoryJpaRepository.findProductCategoryList(anyInt(), any(), anyInt()))
                .thenReturn(new PageImpl<>(List.of(
                        new ProductCategoryInfo(1, "coffee"),
                        new ProductCategoryInfo(2, "tea")
                )));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/product/category")
                        .param("productCategoryId", "0")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 카테고리 조회 API를 사용 가능합니다.")
    public void 상품_카테고리_데이터_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productCategoryJpaRepository.findProductCategoryById(anyInt(), anyInt()))
                .thenReturn(new ProductCategoryDetail(1, "coffee", "Y", LocalDateTime.now()));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/product/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 카테고리 수정 API를 사용 가능합니다.")
    public void 상품_카테고리_수정_성공했습니다() throws Exception {
        // Given
        RequestProductCategoryUpdateDto request = RequestProductCategoryUpdateDto.builder()
                .productCategoryId(1)
                .name("tea")
                .exposeYn("Y")
                .build();

        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productCategoryJpaRepository.findProductCategoryWithUserIdById(anyInt())).thenReturn(new ProductCategoryWithUserId(1, 1));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/product/category")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 카테고리 삭제 API를 사용 가능합니다.")
    public void 상품_카테고리_삭제_성공했습니다() throws Exception {
        // Given
        List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2, 3));
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(productCategoryJpaRepository.isExistProductCategory(anyInt(), anyList())).thenReturn(false);

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/product/category")
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

    // ** Test User 생성
    private User generateUser() {
        return User.builder().id(0).phoneNumber("010-1234-5678").password("1234").build();
    }


    // ** Test Product Category 생성
    private ProductCategory generateProductCategory() {
        return ProductCategory.builder().name("tea").exposeYn("Y").build();
    }
}
