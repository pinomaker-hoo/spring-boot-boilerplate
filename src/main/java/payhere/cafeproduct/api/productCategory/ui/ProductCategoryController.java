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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategorySaveDto;
import payhere.cafeproduct.api.productCategory.event.dto.RequestProductCategoryUpdateDto;
import payhere.cafeproduct.api.productCategory.service.ProductCategoryService;
import payhere.cafeproduct.global.dto.SwaggerExampleValue;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.jwt.JwtTokenExtractor;

import java.util.List;

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

    @Operation(summary = "Find Product Category List Using Pagination", description = "상품 카테고리 리스트 조회, 페이징 네이션을 사용하여 데이터 리스트를 조회합니다. productCategoryId는 최초 조회시 0을 넣어주면 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 카테고리 리스트를 조회합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_FIND_LIST_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @GetMapping
    private ResponseEntity<?> findProductCategoryList(HttpServletRequest request,
                                                      @RequestParam(name = "productCategoryId", defaultValue = "0", required = true) Integer productCategoryId,
                                                      @RequestParam(name = "page", defaultValue = "0", required = true) int page,
                                                      @RequestParam(name = "size", defaultValue = "10", required = true) int size
    ) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);
        Pageable pageable = PageRequest.of(page, size);

        return productCategoryService.findProductCategoryList(userDetailDto, pageable, productCategoryId);
    }

    @Operation(summary = "Find Product Category By Id", description = "상품 카테고리 조회, 상품 카테고리 ID를 기반으로 상품 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 카테고리 리스트를 조회합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_FIND_ONE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "404", description = "상품 카테고리 정보를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_FIND_ONE_NOT_FOUND_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @GetMapping("/{id}")
    private ResponseEntity<?> findProductCategoryById(HttpServletRequest request, @PathVariable(name = "id") Integer productCategoryId) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productCategoryService.findProductCategoryById(userDetailDto, productCategoryId);
    }

    @Operation(summary = "Save Product Category", description = "상품 카테고리 생성, 이름과 노출 여부를 받아 상품 카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 카테고리 정보를 수정합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_UPDATE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "403", description = "해당 카테고리를 수정할 권한이 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_UPDATE_FORBIDDEN_RESPONSE)})),
            @ApiResponse(responseCode = "404", description = "상품 카테고리 정보를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_UPDATE_NOT_FOUND_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @PutMapping
    public ResponseEntity<?> updateProductCategory(HttpServletRequest request, @Valid @RequestBody RequestProductCategoryUpdateDto dto) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productCategoryService.updateProductCategory(userDetailDto, dto);
    }

    @Operation(summary = "Delete Product Category", description = "상품 카테고리 삭제, ID를 기반으로 권한 체크 후 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 카테고리를 삭제합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_DELETE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "403", description = "해당 카테고리를 삭제할 권한이 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_CATEGORY_DELETE_FORBIDDEN_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @DeleteMapping
    public ResponseEntity<?> deleteProductCategory(HttpServletRequest request, @RequestParam(name = "id", required = true) List<Integer> ids) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productCategoryService.deleteProductCategory(userDetailDto, ids);
    }
}
