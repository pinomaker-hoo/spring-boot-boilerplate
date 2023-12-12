package payhere.cafeproduct.global.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class CommonResponse {
    public static ResponseEntity<Object> createResponse(final int code, final String message, Object data) {
        return ApiResponse.builder().meta(new MetaResponse(code, message)).data(data).buildObject();
    }
}
