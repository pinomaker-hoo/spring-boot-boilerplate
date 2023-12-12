package payhere.cafeproduct.api.user.repository;


public interface UserJpaCustomRepository {
    boolean existByPhoneNumber(String phoneNumber);

}
