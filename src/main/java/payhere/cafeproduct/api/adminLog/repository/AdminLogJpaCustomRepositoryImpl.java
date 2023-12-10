package payhere.cafeproduct.api.adminLog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminLogJpaCustomRepositoryImpl implements AdminLogJpaCustomRepository{
    private final JPAQueryFactory queryFactory;
}
