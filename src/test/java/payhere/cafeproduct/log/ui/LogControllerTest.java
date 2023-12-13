package payhere.cafeproduct.log.ui;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import payhere.cafeproduct.api.log.event.vo.LogDetail;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.log.service.LogServiceImpl;
import payhere.cafeproduct.api.log.ui.LogController;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.LogType;
import payhere.cafeproduct.global.enums.UserRole;
import payhere.cafeproduct.global.jwt.JwtTokenExtractor;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Log Controller test")
public class LogControllerTest {
    @InjectMocks
    private LogController logController;

    @Mock
    private LogServiceImpl logService;

    @Mock
    private JwtTokenExtractor jwtTokenExtractor;

    @Mock
    private LogJpaRepository logJpaRepository;

    @Spy
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void initEnvironment() {
        mockMvc = MockMvcBuilders.standaloneSetup(logController).build();
    }

    @Test
    @DisplayName("로그 리스트 조회 API를 사용할 수 있습니다.")
    public void 로그_리스트_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();

        // Mock
        when(jwtTokenExtractor.extractUserId(any())).thenReturn(userDetailDto);
        when(logJpaRepository.findLogList(anyInt(), anyLong(), any()))
                .thenReturn(new PageImpl<>(List.of(
                        new LogDetail(1L, LogType.LOGIN, "로그인 했습니다.", null, 1, LocalDateTime.now())
                )));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/log")
                        .param("logId", "0")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().is(200))
                .andDo(print());
    }


    // ** Test UserDetailDto 생성
    private UserDetailDto generateUserDetailDto() {
        return UserDetailDto.builder().userId(1).role(UserRole.ROLE_MEMBER).build();
    }
}
