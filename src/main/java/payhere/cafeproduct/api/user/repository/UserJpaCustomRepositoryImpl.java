package payhere.cafeproduct.api.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import payhere.cafeproduct.api.user.domain.QUser;
import payhere.cafeproduct.api.user.event.vo.LoginUser;

@RequiredArgsConstructor
public class UserJpaCustomRepositoryImpl implements UserJpaCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existByPhoneNumber(String phoneNumber) {
        QUser u = QUser.user;

        Integer fetchOne = queryFactory
                .selectOne()
                .from(u)
                .where(u.phoneNumber.eq(phoneNumber))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    public LoginUser findUserByPhoneNumber(String phoneNumber) {
        QUser u = QUser.user;

        return queryFactory.select(
                        Projections.constructor(
                                LoginUser.class,
                                u.id,
                                u.phoneNumber,
                                u.password
                        )
                ).from(u)
                .where(u.phoneNumber.eq(phoneNumber)).fetchOne();
    }
}
