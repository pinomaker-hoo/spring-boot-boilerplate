package payhere.cafeproduct.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserJpaRepository userJpaRepository;
}
