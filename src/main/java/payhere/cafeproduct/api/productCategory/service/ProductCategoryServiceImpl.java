package payhere.cafeproduct.api.productCategory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.productCategory.repository.ProductCategoryJpaRepository;

@RequiredArgsConstructor
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryJpaRepository productCategoryJpaRepository;
}
