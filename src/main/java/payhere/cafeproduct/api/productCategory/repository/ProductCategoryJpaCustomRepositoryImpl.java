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
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryInfo;
import payhere.cafeproduct.api.productCategory.event.vo.ProductCategoryWithUserId;

import java.util.List;

@RequiredArgsConstructor
public class ProductCategoryJpaCustomRepositoryImpl implements ProductCategoryJpaCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductCategoryInfo> findProductCategoryList(Integer userId, Pageable pageable, Integer productCategoryId) {
        QProductCategory pc = QProductCategory.productCategory;
        QueryResults<ProductCategoryInfo> list = queryFactory.select(
                        Projections.constructor(
                                ProductCategoryInfo.class,
                                pc.id,
                                pc.name
                        )).from(pc)
                .where(pc.user.id.eq(userId)
                        .and(pc.delYn.eq("N")).and(
                                ltProductCategoryId(productCategoryId)
                        ))
                .orderBy(pc.id.desc())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl(list.getResults(), pageable, list.getTotal());
    }

    @Override
    public ProductCategoryDetail findProductCategoryById(Integer userId, Integer productCategoryId) {
        QProductCategory pc = QProductCategory.productCategory;

        return queryFactory.select(
                Projections.constructor(
                        ProductCategoryDetail.class,
                        pc.id,
                        pc.name,
                        pc.exposeYn,
                        pc.createdDate
                )).from(pc).where(
                pc.id.eq(productCategoryId).and(pc.user.id.eq(userId)).and(pc.delYn.eq("N"))
        ).fetchOne();
    }

    @Override
    public ProductCategoryWithUserId findProductCategoryWithUserIdById(Integer productCategoryId) {
        QProductCategory pc = QProductCategory.productCategory;

        return queryFactory.select(
                Projections.constructor(
                        ProductCategoryWithUserId.class,
                        pc.id,
                        pc.user.id
                )).from(pc).where(
                pc.id.eq(productCategoryId).and(pc.delYn.eq("N"))
        ).fetchOne();
    }

    @Override
    public boolean isExistProductCategory(Integer userId, List<Integer> ids) {
        QProductCategory pc = QProductCategory.productCategory;

        long count = 0;

        count = queryFactory.select(pc.id.count())
                .from(pc)
                .where(pc.user.id.ne(userId).and(pc.id.in(ids)).and(pc.delYn.eq("N")))
                .fetchCount();

        return count > 0 ? true : false;
    }

    private BooleanExpression ltProductCategoryId(Integer productCategoryId) {
        if (productCategoryId.equals(0)) {
            return null;
        }

        return QProductCategory.productCategory.id.lt(productCategoryId);
    }
}
