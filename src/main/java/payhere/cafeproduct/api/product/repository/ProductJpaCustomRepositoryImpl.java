package payhere.cafeproduct.api.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductJpaCustomRepositoryImpl implements ProductJpaCustomRepository {
    private final JPAQueryFactory queryFactory;
}
