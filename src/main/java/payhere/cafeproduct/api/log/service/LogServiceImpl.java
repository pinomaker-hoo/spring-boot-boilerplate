package payhere.cafeproduct.api.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.log.repository.LogJpaRepository;

@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {
    private final LogJpaRepository logJpaRepository;
}
