package payhere.cafeproduct.api.adminLog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payhere.cafeproduct.api.adminLog.repository.AdminLogJpaRepository;

@RequiredArgsConstructor
@Service
public class AdminLogServiceImpl implements AdminLogService{
    private final AdminLogJpaRepository adminLogJpaRepository;
}
