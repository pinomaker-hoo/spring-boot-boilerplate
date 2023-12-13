package payhere.cafeproduct.api.product.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import payhere.cafeproduct.api.product.domain.QProduct;
import payhere.cafeproduct.api.product.event.vo.ProductDetail;
import payhere.cafeproduct.api.product.event.vo.ProductWithUserId;
import payhere.cafeproduct.api.productCategory.domain.QProductCategory;

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

    @Override
    public Page<ProductDetail> findProductList(Integer userId, String name, Long productId, Pageable pageable) {
        QProduct p = QProduct.product;

        QueryResults<ProductDetail> list = queryFactory.select(
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
                        p.productCategory.user.id.eq(userId).and(
                                p.delYn.eq("N").and(
                                        ltProductId(productId)
                                ).and(
                                        p.name.contains(name).or(p.nameConsonant.contains(name))
                                )
                        )).orderBy(p.id.desc())
                .limit(pageable.getPageSize()).fetchResults();

        return new PageImpl(list.getResults(), pageable, list.getTotal());
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId.equals(0L)) {
            return null;
        }

        return QProductCategory.productCategory.id.lt(productId);
    }
}
