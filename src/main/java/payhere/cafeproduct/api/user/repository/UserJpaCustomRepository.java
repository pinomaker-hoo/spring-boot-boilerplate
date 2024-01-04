package payhere.cafeproduct.api.user.repository;


import payhere.cafeproduct.api.user.event.vo.LoginUser;

public interface UserJpaCustomRepository {
    boolean existByUsername(String username);

    LoginUser findUserByUsername(String username);
}
