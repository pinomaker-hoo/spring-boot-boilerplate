package payhere.cafeproduct.global.handler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import payhere.cafeproduct.global.dto.ApiResponse;
import payhere.cafeproduct.global.dto.MetaResponse;
import payhere.cafeproduct.global.exception.*;

import java.rmi.ServerException;

import static org.springframework.http.HttpStatus.*;

/**
 * 전역 에러 처리 핸들러
 */
@Slf4j
@NoArgsConstructor
@RestControllerAdvice(basePackages = {"payhere.cafeproduct"})
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UnAuthenticationException.class
    })
    public ResponseEntity<Object> handleUnauthorizedException(Exception e) {
        log.info("Hello world");
        return errorResponse(e, UNAUTHORIZED);
    }

    @ExceptionHandler({
            BadRequestException.class
    })
    public ResponseEntity<Object> handleBadRequestException(Exception e) {
        return errorResponse(e, BAD_REQUEST);
    }

    @ExceptionHandler({
            ValidationException.class,
    })
    public ResponseEntity<Object> handleValidationException(Exception e) {
        log.error("[LOG] {} ({}) : {}", BAD_REQUEST, BAD_REQUEST.value(), e.getMessage());
        return ApiResponse.builder().meta(new MetaResponse(BAD_REQUEST, "입력 값이 잘못되었습니다.")).data(null).buildObject();
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(Exception e) {
        return errorResponse(e, FORBIDDEN);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception e) {
        return errorResponse(e, NOT_FOUND);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleInternalServerError(Exception e) {
        return errorResponse(e, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServerException.class)
    protected ResponseEntity<Object> handleServerException(final ServerException e) {
        return errorResponse(e, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> errorResponse(Exception e, HttpStatus httpStatus) {
        log.error("[LOG] {} ({}) : {}", httpStatus, httpStatus.value(), e.getMessage());
        return ApiResponse.builder().meta(new MetaResponse(httpStatus, e.getMessage())).data(null).buildObject();
    }
}
