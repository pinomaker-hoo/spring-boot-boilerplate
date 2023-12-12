package payhere.cafeproduct.api.log.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogJpaCustomRepositoryImpl implements LogJpaCustomRepository {
    private final JPAQueryFactory queryFactory;
}
