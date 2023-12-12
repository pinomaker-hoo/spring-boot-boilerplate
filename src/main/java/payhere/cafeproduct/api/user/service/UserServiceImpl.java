package payhere.cafeproduct.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.api.user.event.dto.RequestUserSaveDto;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.CommonResponse;
import payhere.cafeproduct.global.exception.BadRequestException;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService{
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> saveUser(RequestUserSaveDto dto) throws Exception {
        boolean existUserByPhoneNumber = userJpaRepository.existByPhoneNumber(dto.getPhoneNumber());

        log.info("Exist User By Phone Number : {}", existUserByPhoneNumber);

        if (existUserByPhoneNumber) {
            throw new BadRequestException("이미 가입한 전화번호 입니다.");
        }

        userJpaRepository.save(User.builder()
                .phoneNumber(dto.getPhoneNumber())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build());

        return CommonResponse.createResponse(HttpStatus.OK.value(), "생성했습니다.", null);
    }

}
