package payhere.cafeproduct.api.user.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUserSaveDto {
    @Schema(example = "admin")
    @Size(max = 30, message = "30자 이내로 입력하세요.")
    @NotNull
    private String username;

    @Schema(example = "qwer1595")
    @Size(min = 8, max = 12, message = "8자에서 12자 이내로 입력하세요.")
    @NotNull
    private String password;
}
