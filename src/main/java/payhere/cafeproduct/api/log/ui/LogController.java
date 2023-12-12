package payhere.cafeproduct.api.log.ui;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payhere.cafeproduct.api.log.service.LogService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/log")
@Tag(name = "Log API", description = "로그 관련 API")
@Slf4j
public class LogController {
    private final LogService logService;
}
