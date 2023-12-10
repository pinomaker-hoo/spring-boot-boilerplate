package payhere.cafeproduct.api.productCategory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductCategoryJpaCustomRepositoryImpl {
    private final JPAQueryFactory queryFactory;
}
