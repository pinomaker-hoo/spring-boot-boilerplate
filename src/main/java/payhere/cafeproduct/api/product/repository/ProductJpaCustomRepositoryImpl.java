package payhere.cafeproduct.api.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import payhere.cafeproduct.api.product.domain.QProduct;
import payhere.cafeproduct.api.product.event.vo.ProductDetail;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;

import java.util.List;

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

    @Override
    public boolean existProduct(Integer userId, List<Long> ids) {
        QProduct p = QProduct.product;

        long count = 0;

        count = queryFactory.select(p.id.count())
                .from(p)
                .where(p.productCategory.user.id.ne(userId).and(
                        p.id.in(ids)
                )).fetchCount();

        return count > 0 ? true : false;
    }

    @Override
    public ProductDetail findProductById(Integer userId, Long productId) {
        QProduct p = QProduct.product;

        return queryFactory.select(
                Projections.constructor(
                        ProductDetail.class,
                        p.id,
                        p.price,
                        p.cost,
                        p.name,
                        p.code,
                        p.expirationDate,
                        p.size,
                        p.soldOutYn,
                        p.exposeYn,
                        p.productCategory.name,
                        p.createdDate
                )).from(p).where(
                p.id.eq(productId).and(p.productCategory.user.id.eq(userId).and(
                        p.delYn.eq("N")
                ))
        ).fetchOne();
    }
}
