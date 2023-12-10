package payhere.cafeproduct.api.adminLog.ui;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payhere.cafeproduct.api.adminLog.service.AdminLogService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/log")
@Tag(name = "Admin Log API", description = "관리자 로그 관련 API")
@Slf4j
public class AdminLogController {
    private final AdminLogService adminLogService;
}
