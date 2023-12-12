package payhere.cafeproduct.productCategory.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;
import payhere.cafeproduct.api.productCategory.service.ProductCategoryServiceImpl;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;

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

        // Mock

        // When

        // Then
    }

    @Test
    @DisplayName("상품 카테고리 생성 - 상품 카테고리 생성에 성공했습니다.")
    public void 상품_카테고리_생성_성공했습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
    }

    @Test
    @DisplayName("상품 카테고리 리스트 조회 - 상품 카테고리 리스트 조회에 성공했습니다.")
    public void 상품_카테고리_리스트_조회_성공했습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
    }

    @Test
    @DisplayName("상품 카테고리 조회 - 상품 카테고리 정보를 찾을 수 없습니다.")
    public void 상품_카테고리_데이터_조회_데이터를_찾을_수_없습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
    }

    @Test
    @DisplayName("상품 카테고리 조회 - ID 기반의 상품 카테고리 조회에 성공했습니다.")
    public void 상품_카테고리_데이터_조회_성공했습니다() throws Exception {
        // Given

        // Mock

        // When

        // Then
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
}
