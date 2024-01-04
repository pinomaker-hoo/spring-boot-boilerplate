package com.mars.api.user.ui;

import com.mars.global.dto.CommonResponse;
import com.mars.global.dto.SwaggerExampleValue;
import com.mars.global.dto.UserDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mars.global.utils.PermissionUtils.getUserDetailDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User API", description = "유저 관련 API")
@Slf4j
public class UserController {
    @Operation(summary = "Sample", description = "샘플 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "샘플.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.SAMPLE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않습니다.", content = @Content(mediaType = "application/json", examples = {@ExampleObject(value = SwaggerExampleValue.UN_AUTHENTICATION_RESPONSE)})),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExampleValue.INTERNAL_SERVER_ERROR_RESPONSE)))})
    @GetMapping
    public ResponseEntity<?> sample(HttpServletRequest request) throws Exception {
        UserDetailDto userDetailDto1 = getUserDetailDto(request);

        return CommonResponse.createResponseMessage(HttpStatus.OK.value(), "샘플");
    }
}
