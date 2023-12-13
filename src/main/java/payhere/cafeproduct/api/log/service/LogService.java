package payhere.cafeproduct.api.log.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.global.dto.UserDetailDto;

public interface LogService {
    ResponseEntity<?> findLog(UserDetailDto userDetailDto, Long logId, Pageable pageable);
}
