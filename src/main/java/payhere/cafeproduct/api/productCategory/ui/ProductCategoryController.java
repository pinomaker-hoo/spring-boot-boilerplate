package payhere.cafeproduct.api.productCategory.ui;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payhere.cafeproduct.api.productCategory.service.ProductCategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product/category")
@Tag(name = "Product Category API", description = "제품 카테고리 관련 API")
@Slf4j
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
}
