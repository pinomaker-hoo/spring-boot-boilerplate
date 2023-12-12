package payhere.cafeproduct.api.user.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUserSaveDto {
    @Schema(example = "010-6305-7848")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 형식이 옳지 않습니다.")
    @NotNull


    private String phoneNumber;

    @Schema(example = "qwer1595")
    @NotNull
    private String password;
}
