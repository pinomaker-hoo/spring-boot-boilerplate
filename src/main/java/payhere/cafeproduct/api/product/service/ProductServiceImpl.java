package payhere.cafeproduct.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.product.repository.ProductJpaRepository;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductJpaRepository productJpaRepository;
}
