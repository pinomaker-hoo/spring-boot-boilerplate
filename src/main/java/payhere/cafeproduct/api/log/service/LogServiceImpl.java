package payhere.cafeproduct.api.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.log.event.vo.LogDetail;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;
import payhere.cafeproduct.global.dto.CommonResponse;
import payhere.cafeproduct.global.dto.Pagination;
import payhere.cafeproduct.global.dto.UserDetailDto;

@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {
    private final LogJpaRepository logJpaRepository;

    @Override
    public ResponseEntity<?> findLog(UserDetailDto userDetailDto, Long logId, Pageable pageable) {
        // ** 로그 리스트 조회
        Page<LogDetail> response = logJpaRepository.findLogList(userDetailDto.getUserId(), logId, pageable);

        // ** Response data로 Format
        Pagination data = Pagination.builder().totalPages(response.getTotalPages()).currentPage(response.getNumber()).totalItems(response.getTotalElements()).data(response.getContent()).build();

        return CommonResponse.createResponse(HttpStatus.OK.value(), "로그 리스트를 조회합니다.", data);
    }
}
