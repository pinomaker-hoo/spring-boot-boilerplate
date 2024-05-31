package com.pinomaker.api.user.service;

import com.pinomaker.api.user.domain.User;
import com.pinomaker.api.user.event.dto.RequestUserLoginDto;
import com.pinomaker.api.user.repository.UserJpaRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pinomaker.api.user.event.dto.RequestTokenReissueDto;
import com.pinomaker.api.user.event.dto.RequestUserSaveDto;
import com.pinomaker.api.user.event.vo.LoginUser;
import com.pinomaker.global.dto.CommonResponse;
import com.pinomaker.global.dto.TokenDto;
import com.pinomaker.global.enums.UserRole;
import com.pinomaker.global.exception.BadRequestException;
import com.pinomaker.global.exception.NotFoundException;
import com.pinomaker.global.jwt.JwtTokenProvider;
import com.pinomaker.global.jwt.JwtTokenValidator;
import com.pinomaker.global.utils.EncryptionUtils;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenValidator jwtTokenValidator;
    private final EncryptionUtils encryptionUtils;

    /**
     * 회원가입 메서드
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<?> saveUser(RequestUserSaveDto dto) {
        // ** 유저네임 기반 유저 조회
        boolean existUserByPhoneNumber = userJpaRepository.existByUsername(dto.getUsername());

        // ** 중복 유저 존재
        if (existUserByPhoneNumber) {
            throw new BadRequestException("이미 사용 중인 아이디 입니다.");
        }

        // ** 유저 생성
        userJpaRepository.save(User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build());

        return CommonResponse.createResponseMessage(HttpStatus.OK.value(), "회원가입에 성공하였습니다.");
    }


    /**
     * 로그인 메서드
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity<?> loginUser(RequestUserLoginDto dto) {
        // ** Login 유저 조회
        LoginUser loginUser = userJpaRepository.findUserByUsername(dto.getUsername());

        // ** 데이터 조회 실패
        if (loginUser == null) {
            throw new NotFoundException("유저를 찾을 수 없습니다.");
        }

        // ** 패스워드가 같지 않음
        if (!passwordEncoder.matches(dto.getPassword(), loginUser.getPassword())) {
            throw new BadRequestException("비밀번호가 같지 않습니다.");
        }

        // ** Token 생성
        TokenDto tokenDto = jwtTokenProvider.issueToken(loginUser.getId(), UserRole.ROLE_MEMBER);

        return CommonResponse.createResponse(HttpStatus.OK.value(), "로그인에 성공했습니다.", tokenDto);
    }

    @Override
    public ResponseEntity<?> reissueToken(RequestTokenReissueDto dto) {
        // ** RefreshToken 유효성 검사
        jwtTokenValidator.validateToken(dto.getRefreshToken());

        // ** 유저 정보 추출
        Claims claims = jwtTokenValidator.getClaimsFromToken(dto.getRefreshToken());

        String encodedUserId = String.valueOf(claims.get("id"));
        String encodedRole = String.valueOf(claims.get("role"));

        // ** 정보 디코딩
        Integer decodedUserId = Integer.valueOf(encryptionUtils.decrypt(encodedUserId));
        UserRole decodedRole = UserRole.valueOf(encryptionUtils.decrypt(encodedRole));

        // ** 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.reissueToken(decodedUserId, decodedRole, dto.getRefreshToken());

        return CommonResponse.createResponse(HttpStatus.OK.value(), "토큰을 재발급 받습니다.", tokenDto);
    }
}
