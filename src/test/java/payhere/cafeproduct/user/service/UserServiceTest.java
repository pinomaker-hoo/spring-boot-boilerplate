package payhere.cafeproduct.user.service;

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
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.user.event.dto.RequestUserLoginDto;
import payhere.cafeproduct.api.user.event.dto.RequestUserSaveDto;
import payhere.cafeproduct.api.user.event.vo.LoginUser;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.api.user.service.UserServiceImpl;
import payhere.cafeproduct.global.exception.BadRequestException;
import payhere.cafeproduct.global.exception.NotFoundException;
import payhere.cafeproduct.global.jwt.JwtTokenProvider;

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
    private LogJpaRepository logJpaRepository;

    @Test
    @DisplayName("회원가입 - 전화번호 중복일 수 없습니다.")
    public void 회원가입_전화번호는_중복일_수_없습니다() throws Exception {
        // Given
        RequestUserSaveDto request = RequestUserSaveDto.builder()
                .phoneNumber("010-1234-5678")
                .password("qwer1595")
                .build();

        // Mock
        when(userJpaRepository.existByPhoneNumber(anyString())).thenReturn(true);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.saveUser(request);
        });

        // Then
        assertEquals("이미 가입한 전화번호 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 - 성공")
    public void 회원가입_성공했습니다() throws Exception {
        // Given
        RequestUserSaveDto request = RequestUserSaveDto.builder()
                .phoneNumber("010-1234-5678")
                .password("qwer1595")
                .build();

        // Mock
        when(userJpaRepository.existByPhoneNumber(request.getPhoneNumber()))
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
                .phoneNumber("010-1234-5678")
                .password("qwer1595")
                .build();

        // Mock
        when(userJpaRepository.existByPhoneNumber(anyString())).thenReturn(false);

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
                .phoneNumber("010-1234-5678")
                .password("qwer1595")
                .build();

        LoginUser loginUser = new LoginUser(0, request.getPhoneNumber(), "");

        // Mock
        when(userJpaRepository.findUserByPhoneNumber(anyString())).thenReturn(loginUser);
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
                .phoneNumber("010-1234-5678")
                .password("qwer1595")
                .build();

        LoginUser loginUser = new LoginUser(0, request.getPhoneNumber(), request.getPassword());

        // Mock
        when(userJpaRepository.findUserByPhoneNumber(anyString())).thenReturn(loginUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // When
        ResponseEntity<?> response = userService.loginUser(request);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
