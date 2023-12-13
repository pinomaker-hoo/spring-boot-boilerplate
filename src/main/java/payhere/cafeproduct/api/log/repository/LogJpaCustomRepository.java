package payhere.cafeproduct.api.log.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import payhere.cafeproduct.api.log.event.vo.LogDetail;

public interface LogJpaCustomRepository {
    Page<LogDetail> findLogList(Integer userId, Long logId, Pageable pageable);
}
