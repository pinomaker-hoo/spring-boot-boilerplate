package payhere.cafeproduct.api.product.ui;

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
import payhere.cafeproduct.api.product.event.dto.RequestProductSaveDto;
import payhere.cafeproduct.api.product.event.dto.RequestProductUpdateDto;
import payhere.cafeproduct.api.product.service.ProductService;
import payhere.cafeproduct.global.dto.SwaggerExampleValue;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.jwt.JwtTokenExtractor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product API", description = "제품 관련 API")
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Operation(summary = "Save Product", description = "상품 생성, 상품 정보를 받아 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품을 생성합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_SAVE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "404", description = "상품 카테고리 정보를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_SAVE_NOT_FOUND_CATEGORY_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @PostMapping
    public ResponseEntity<?> saveProductCategory(HttpServletRequest request, @Valid @RequestBody RequestProductSaveDto dto) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productService.saveProduct(userDetailDto, dto);
    }

    @Operation(summary = "Find Product List Using Pagination", description = "상품 리스트 조회, 페이징 네이션을 사용하여 데이터 리스트를 조회합니다. productId는 최초 조회시 0을 넣어주면 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 리스트를 조회합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_FIND_LIST_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @GetMapping
    private ResponseEntity<?> findProductCategoryList(HttpServletRequest request,
                                                      @RequestParam(name = "productId", defaultValue = "0", required = true) Long productId,
                                                      @RequestParam(name = "name", defaultValue = "", required = false) String name,
                                                      @RequestParam(name = "page", defaultValue = "0", required = true) int page,
                                                      @RequestParam(name = "size", defaultValue = "10", required = true) int size
    ) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);
        Pageable pageable = PageRequest.of(page, size);

        return productService.findProductList(userDetailDto, name, productId, pageable);
    }

    @Operation(summary = "Find Product By Id", description = "상품 조회, 상품 ID를 기반으로 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품을 조회합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_FIND_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "404", description = "상품 정보를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_FIND_NOT_FOUND_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @GetMapping("/{id}")
    private ResponseEntity<?> findProductCategoryById(HttpServletRequest request, @PathVariable(name = "id") Long productId) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productService.findProductById(userDetailDto, productId);
    }

    @Operation(summary = "Update Product", description = "상품 수정, 상품 정보를 받아 권한 체크 후 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품을 수정합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_UPDATE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "403", description = "상품을 수정할 권한이 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_UPDATE_FORBIDDEN_RESPONSE)})),
            @ApiResponse(responseCode = "404", description = "상품 정보를 찾을 수 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_UPDATE_NOT_FOUND_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @PutMapping
    public ResponseEntity<?> updateProduct(HttpServletRequest request, @Valid @RequestBody RequestProductUpdateDto dto) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productService.updateProduct(userDetailDto, dto);
    }

    @Operation(summary = "Delete Category", description = "상품 삭제, ID를 기반으로 권한 체크 후 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품을 삭제합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.PRODUCT_DELETE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "403", description = "해당 상품을 삭제할 권한이 없습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.PRODUCT_DELETE_FORBIDDEN_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @DeleteMapping
    public ResponseEntity<?> deleteProduct(HttpServletRequest request, @RequestParam(name = "id", required = true) List<Long> ids) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);

        return productService.deleteProduct(userDetailDto, ids);
    }
}
