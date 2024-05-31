package com.pinomaker.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.pinomaker.api.user.event.dto.RequestTokenReissueDto;
import com.pinomaker.api.user.event.dto.RequestUserLoginDto;
import com.pinomaker.api.user.event.dto.RequestUserSaveDto;
import com.pinomaker.api.user.event.vo.LoginUser;
import com.pinomaker.api.user.repository.UserJpaRepository;
import com.pinomaker.api.user.service.UserServiceImpl;
import com.pinomaker.global.enums.UserRole;
import com.pinomaker.global.exception.BadRequestException;
import com.pinomaker.global.exception.NotFoundException;
import com.pinomaker.global.jwt.JwtTokenProvider;
import com.pinomaker.global.jwt.JwtTokenValidator;
import com.pinomaker.global.utils.EncryptionUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("User test")
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private JwtTokenValidator jwtTokenValidator;

    @Mock
    private EncryptionUtils encryptionUtils;

    @Test
    @DisplayName("회원가입 - 유저 아이디는 중복일 수 없습니다.")
    public void 회원가입_유저_아이디는_중복일_수_없습니다() throws Exception {
        // Given
        RequestUserSaveDto request = RequestUserSaveDto.builder()
                .username("pinomaker")
                .password("qwer1595")
                .build();

        // Mock
        when(userJpaRepository.existByUsername(anyString())).thenReturn(true);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.saveUser(request);
        });

        // Then
        assertEquals("이미 사용 중인 아이디 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 - 성공")
    public void 회원가입_성공했습니다() throws Exception {
        // Given
        RequestUserSaveDto request = RequestUserSaveDto.builder()
                .username("pinomaker")
                .password("qwer1595")
                .build();

        // Mock
        when(userJpaRepository.existByUsername(request.getUsername()))
                .thenReturn(false);

        // When
        ResponseEntity<?> response = userService.saveUser(request);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("로그인 - 유저를 찾을 수 없습니다.")
    public void 로그인_유저를_찾을_수_없습니다() throws Exception {
        // Given
        RequestUserLoginDto request = RequestUserLoginDto.builder()
                .username("pinomaker")
                .password("qwer1595")
                .build();

        // Mock
        when(userJpaRepository.existByUsername(anyString())).thenReturn(false);

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.loginUser(request);
        });

        // Then
        assertEquals("유저를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("로그인 - 비밀번호가 같지 않습니다.")
    public void 로그인_비밀번호가_같지_않습니다() throws Exception {
        // Given
        RequestUserLoginDto request = RequestUserLoginDto.builder()
                .username("pinomaker")
                .password("qwer1595")
                .build();

        LoginUser loginUser = new LoginUser(0, request.getUsername(), "");

        // Mock
        when(userJpaRepository.findUserByUsername(anyString())).thenReturn(loginUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.loginUser(request);
        });

        // Then
        assertEquals("비밀번호가 같지 않습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("로그인 - 로그인에 성공했습니다.")
    public void 로그인_성공했습니다() throws Exception {
        // Given
        RequestUserLoginDto request = RequestUserLoginDto.builder()
                .username("pinomaker")
                .password("qwer1595")
                .build();

        LoginUser loginUser = new LoginUser(0, request.getUsername(), request.getPassword());

        // Mock
        when(userJpaRepository.findUserByUsername(anyString())).thenReturn(loginUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // When
        ResponseEntity<?> response = userService.loginUser(request);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("토큰 재발급 - 토큰 재발급에 성공했습니다.")
    public void 토큰_재발급_성공했습니다() throws Exception {
        // Given
        RequestTokenReissueDto request = RequestTokenReissueDto.builder().refreshToken("eyJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiVUJPVE5VV1VlWUR4U2lraW1lWmZsUSIsImlkIjoiR0lTd2pBczdfYng4djJ4REFfY25uZyIsImlhdCI6MTcwMjQ0NjM5MCwiZXhwIjoxNzAyNDQ4MTkwfQ.OZZk9tEQpARX22edli6huhxlWSnf8xhm8sEVev3PJZJlC_-5slETODlNLjN-eB6nipahx5rUXK3hJfwHqAgUoEPa_F7dJYc_gWkRqnvgp8TMdVw0xVWpCREX7s81hMSixeAL3abA6kFdeDATkhRBdGDcmeHtzBxJC-h9ctSYOa7WB7UbATW4XXr_V-HKAWz0geFiz4mJ6Jk3d0KwKRtsGH_xuQp90AGxmme0AuL9gSn5TtOuaZpIuW_0d7RT-9vH_W1NsA2Qe7i7F93_Jit73DF5gNhy5ZwHMiEZFuGCOIMPZ9E6T3Lz12sd0Q-HZKkaWiiJ2tasSXK1l3fLQWABFQ").build();
        Map<String, Object> claims = new HashMap<>();
        String encodedId = encryptionUtils.encrypt(String.valueOf(1));
        String encodedRole = encryptionUtils.encrypt(String.valueOf(UserRole.ROLE_MEMBER));

        claims.put("id", encodedId);
        claims.put("role", encodedRole);

        Claims jwtClaims = Jwts.claims(claims);

        // Mock
        when(jwtTokenValidator.getClaimsFromToken(request.getRefreshToken())).thenReturn(jwtClaims);
        when(encryptionUtils.decrypt(anyString())).thenReturn("1").thenReturn("ROLE_MEMBER");
        // When
        ResponseEntity<?> response = userService.reissueToken(request);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
