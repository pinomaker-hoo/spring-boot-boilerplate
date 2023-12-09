package payhere.cafeproduct.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.ResponseEntity;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private MetaResponse meta;
    private T data;

    public static <T> ApiResponseBuilder<T> data(final T data) {
        ApiResponseBuilder<T> builder = new ApiResponseBuilder<>();
        builder.data = data;
        return builder;
    }

    private ApiResponse(ApiResponseBuilder<T> builder) {
        this.meta = builder.meta;
        this.data = builder.data;
    }

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    public static class ApiResponseBuilder<T> {
        private MetaResponse meta;
        private T data;

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> meta(MetaResponse meta) {
            this.meta = meta;
            return this;
        }

        public ApiResponse<T> init() {
            return new ApiResponse<>(this);
        }

        public ResponseEntity<Object> buildObject() {
            return new ResponseEntity<>(init(), meta.getCode());
        }
    }
}
