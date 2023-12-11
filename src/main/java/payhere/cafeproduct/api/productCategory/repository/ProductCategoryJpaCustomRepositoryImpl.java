package payhere.cafeproduct.api.productCategory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductCategoryJpaCustomRepositoryImpl implements ProductCategoryJpaCustomRepository {
    private final JPAQueryFactory queryFactory;
}
