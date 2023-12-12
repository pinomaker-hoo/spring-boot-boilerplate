package payhere.cafeproduct.api.productCategory.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import payhere.cafeproduct.api.productCategory.domain.QProductCategory;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryDetail;

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

    @Override
    public Page<ProductCategoryDetail> findProductCategoryList(Integer userId, Pageable pageable, Integer productCategoryId) {
        QProductCategory pc = QProductCategory.productCategory;
        QueryResults<ProductCategoryDetail> list = queryFactory.select(
                        Projections.constructor(
                                ProductCategoryDetail.class,
                                pc.id,
                                pc.name
                        )).from(pc)
                .where(pc.user.id.eq(userId).and(
                        ltProductCategoryId(productCategoryId)
                ))
                .orderBy(pc.id.desc())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl(list.getResults(), pageable, list.getTotal());
    }

    private BooleanExpression ltProductCategoryId(Integer productCategoryId) {
        if (productCategoryId.equals(0)) {
            return null;
        }

        return QProductCategory.productCategory.id.lt(productCategoryId);
    }
}
