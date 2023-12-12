package payhere.cafeproduct.api.productCategory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import payhere.cafeproduct.api.productCategory.domain.QProductCategory;

@RequiredArgsConstructor
public class ProductCategoryJpaCustomRepositoryImpl implements ProductCategoryJpaCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public long findProductCategoryCountByUserId(Integer userId) {
        QProductCategory pc = QProductCategory.productCategory;

        return queryFactory.select(pc.id.count())
                .from(pc)
                .where(pc.user.id.eq(userId))
                .fetchCount();
    }
}
