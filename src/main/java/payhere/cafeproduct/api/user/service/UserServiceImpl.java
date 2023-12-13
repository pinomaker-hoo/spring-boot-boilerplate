package payhere.cafeproduct.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.log.domain.Log;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.api.user.event.dto.RequestUserLoginDto;
import payhere.cafeproduct.api.user.event.dto.RequestUserSaveDto;
import payhere.cafeproduct.api.user.event.vo.LoginUser;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.global.dto.CommonResponse;
import payhere.cafeproduct.global.dto.TokenDto;
import payhere.cafeproduct.global.enums.LogType;
import payhere.cafeproduct.global.enums.UserRole;
import payhere.cafeproduct.global.exception.BadRequestException;
import payhere.cafeproduct.global.exception.NotFoundException;
import payhere.cafeproduct.global.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final LogJpaRepository logJpaRepository;

    @Override
    public ResponseEntity<?> saveUser(RequestUserSaveDto dto) throws Exception {
        // ** 전화번호 기반 유저 조회
        boolean existUserByPhoneNumber = userJpaRepository.existByPhoneNumber(dto.getPhoneNumber());

        log.info("전화번호를 가진 유저 존재 여부 : {}", existUserByPhoneNumber);

        // ** 중복 유저 존재
        if (existUserByPhoneNumber) {
            throw new BadRequestException("이미 가입한 전화번호 입니다.");
        }

        // ** 유저 생성
        userJpaRepository.save(User.builder()
                .phoneNumber(dto.getPhoneNumber())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build());

        return CommonResponse.createResponse(HttpStatus.OK.value(), "회원가입에 성공하였습니다.", null);
    }


    @Override
    public ResponseEntity<?> loginUser(RequestUserLoginDto dto) throws Exception {
        // ** Login 유저 조회
        LoginUser loginUser = userJpaRepository.findUserByPhoneNumber(dto.getPhoneNumber());

        // ** 데이터 조회 실패
        if (loginUser == null) {
            throw new NotFoundException("유저를 찾을 수 없습니다.");
        }

        // ** 패스워드가 같지 않음
        if (!passwordEncoder.matches(dto.getPassword(), loginUser.getPassword())) {
            throw new BadRequestException("비밀번호가 같지 않습니다.");
        }

        // ** Token 생성
        TokenDto tokenDto = jwtTokenProvider.issueToken(Long.valueOf(loginUser.getId()), UserRole.ROLE_MEMBER);

        // ** 로그인 기록 생성
        logJpaRepository.save(Log.builder().logType(LogType.LOGIN).log("로그인 했습니다.").userId(loginUser.getId()).build());

        return CommonResponse.createResponse(HttpStatus.OK.value(), "로그인에 성공했습니다.", tokenDto);
    }
}
