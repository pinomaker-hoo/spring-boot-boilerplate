package payhere.cafeproduct.api.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import payhere.cafeproduct.api.user.domain.QUser;
import payhere.cafeproduct.api.user.event.vo.LoginUser;

@RequiredArgsConstructor
public class UserJpaCustomRepositoryImpl implements UserJpaCustomRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * username 기반 데이터 존재 여부 조회
     * @param username
     * @return
     */
    @Override
    public boolean existByUsername(String username) {
        QUser u = QUser.user;

        Integer fetchOne = queryFactory
                .selectOne()
                .from(u)
                .where(u.username.eq(username))
                .fetchFirst();

        return fetchOne != null;
    }

    /**
     * username 기반 user 조회
     * @param username
     * @return
     */
    @Override
    public LoginUser findUserByUsername(String username) {
        QUser u = QUser.user;

        return queryFactory.select(
                        Projections.constructor(
                                LoginUser.class,
                                u.id,
                                u.username,
                                u.password
                        )
                ).from(u)
                .where(u.username.eq(username)).fetchOne();
    }
}
