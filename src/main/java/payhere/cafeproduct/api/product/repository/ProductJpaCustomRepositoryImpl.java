package payhere.cafeproduct.api.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import payhere.cafeproduct.api.product.domain.QProduct;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;

@RequiredArgsConstructor
public class ProductJpaCustomRepositoryImpl implements ProductJpaCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public ProductWithUserId findProductById(Long id) {
        QProduct p = QProduct.product;


        return queryFactory.select(
                        Projections.constructor(
                                ProductWithUserId.class,
                                p.id,
                                p.productCategory.user.id
                        )
                ).from(p)
                .where(p.id.eq(id).and(p.delYn.eq("N")))
                .fetchOne();
    }
}
