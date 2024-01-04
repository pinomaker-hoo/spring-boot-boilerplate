package payhere.cafeproduct.api.user.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUserLoginDto {
    @Schema(example = "010-6305-7848")
    @Size(max = 30, message = "30자 이내로 입력하세요.")
    @NotNull
    private String username;

    @Schema(example = "qwer1595")
    @Size(min = 8, max = 12, message = "8자에서 12자 이내로 입력하세요.")
    @NotNull
    private String password;
}
