package com.pinomaker.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.pinomaker.api.user.event.dto.RequestTokenReissueDto;
import com.pinomaker.api.user.event.dto.RequestUserLoginDto;
import com.pinomaker.api.user.event.dto.RequestUserSaveDto;
import com.pinomaker.api.user.event.vo.LoginUser;
import com.pinomaker.api.user.repository.UserJpaRepository;
import com.pinomaker.api.user.service.UserServiceImpl;
import com.pinomaker.api.user.ui.AuthController;
import com.pinomaker.global.enums.UserRole;
import com.pinomaker.global.jwt.JwtTokenProvider;
import com.pinomaker.global.jwt.JwtTokenValidator;
import com.pinomaker.global.utils.EncryptionUtils;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Auth API Test")
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;

    @Mock
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

    @Spy
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void initEnvironment() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("회원가입 API를 사용 가능합니다.")
    public void 회원가입_성공() throws Exception {
        // Given
        RequestUserSaveDto request = RequestUserSaveDto.builder()
                .username("010-1234-5678")
                .password("qwer1595")
                .build();

        // Mock
        when(userJpaRepository.existByUsername(request.getUsername()))
                .thenReturn(false);

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("Login API를 사용 가능합니다.")
    public void 로그인_성공() throws Exception {
        // Given
        RequestUserLoginDto request = RequestUserLoginDto.builder()
                .username("010-1234-5678")
                .password("qwer1595")
                .build();

        LoginUser loginUser = new LoginUser(0, request.getUsername(), request.getPassword());

        // Mock
        when(userJpaRepository.findUserByUsername(anyString())).thenReturn(loginUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    @DisplayName("Token 재발급 API를 사용 가능합니다.")
    public void 토큰_재발급() throws Exception {
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
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }
}
