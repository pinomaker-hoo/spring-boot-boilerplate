package payhere.cafeproduct.log.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.log.event.vo.LogDetail;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.api.log.service.LogServiceImpl;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.enums.LogType;
import payhere.cafeproduct.global.enums.UserRole;
import java.time.LocalDateTime;
import java.util.List;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Log test")
public class LogServiceTest {
    @InjectMocks
    private LogServiceImpl logService;

    @Mock
    private LogJpaRepository logJpaRepository;

    @Test
    @DisplayName("로그 리스트 조회 - 상품 리스트 조회에 성공했습니다.")
    public void 로그_리스트_조회_성공했습니다() throws Exception {
        // Given
        UserDetailDto userDetailDto = generateUserDetailDto();
        Pageable pageable = PageRequest.of(0, 10);
        Long logId = 0L;

        // Mock
        when(logJpaRepository.findLogList(anyInt(), anyLong(), any()))
                .thenReturn(new PageImpl<>(List.of(
                        new LogDetail(1L, LogType.LOGIN, "로그인 했습니다.", null, 1, LocalDateTime.now())
                )));

        // When
        ResponseEntity<?> response = logService.findLog(userDetailDto, logId, pageable);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }


    // ** Test UserDetailDto 생성
    private UserDetailDto generateUserDetailDto() {
        return UserDetailDto.builder().userId(1).role(UserRole.ROLE_MEMBER).build();
    }
}
