package payhere.cafeproduct.api.productCategory.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.api.productCategory.service.ProductCategoryService;
import payhere.cafeproduct.global.dto.SwaggerExampleValue;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.jwt.JwtTokenExtractor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product/category")
@Tag(name = "Product Category API", description = "제품 카테고리 관련 API")
@Slf4j
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Operation(summary = "Save Product Category", description = "상품 카테고리 생성, 이름과 노출 여부를 받아 상품 카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 카테고리를 생성합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_SAVE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "404", description = "로그인한 유저 정보를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_SAVE_NOT_FOUND_USER_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @PostMapping
    public ResponseEntity<?> saveProductCategory(HttpServletRequest request, @Valid @RequestBody RequestProductCategorySaveDto dto) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productCategoryService.saveProductCategory(userDetailDto, dto);
    }
}
