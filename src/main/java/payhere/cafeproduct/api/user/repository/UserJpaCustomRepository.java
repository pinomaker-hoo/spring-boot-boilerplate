package payhere.cafeproduct.api.user.repository;


import payhere.cafeproduct.api.user.event.vo.LoginUser;

public interface UserJpaCustomRepository {
    boolean existByPhoneNumber(String phoneNumber);

    LoginUser findUserByPhoneNumber(String phoneNumber);
}