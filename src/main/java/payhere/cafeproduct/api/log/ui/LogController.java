package payhere.cafeproduct.api.log.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import payhere.cafeproduct.api.log.service.LogService;
import payhere.cafeproduct.global.dto.SwaggerExampleValue;
import payhere.cafeproduct.global.dto.UserDetailDto;
import payhere.cafeproduct.global.jwt.JwtTokenExtractor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/log")
@Tag(name = "Log API", description = "로그 관련 API")
@Slf4j
public class LogController {
    private final LogService logService;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Operation(summary = "Find Log List Using Pagination", description = "로그 리스트 조회, 페이징 네이션을 사용하여 데이터 리스트를 조회합니다. logId는 최초 조회시 0을 넣어주면 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그 리스트를 조회합니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.LOG_FIND_LIST_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @GetMapping
    private ResponseEntity<?> findProductCategoryList(HttpServletRequest request,
                                                      @RequestParam(name = "logId", defaultValue = "0", required = true) Long logId,
                                                      @RequestParam(name = "page", defaultValue = "0", required = true) int page,
                                                      @RequestParam(name = "size", defaultValue = "10", required = true) int size
    ) throws Exception {
        UserDetailDto userDetailDto = jwtTokenExtractor.extractUserId(request);
        Pageable pageable = PageRequest.of(page, size);

        return logService.findLog(userDetailDto, logId, pageable);
    }
}
