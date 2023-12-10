package payhere.cafeproduct.api.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaCustomRepositoryImpl implements UserJpaCustomRepository {
    private final JPAQueryFactory queryFactory;
}
