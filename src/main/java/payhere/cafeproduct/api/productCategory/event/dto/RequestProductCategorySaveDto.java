package payhere.cafeproduct.api.productCategory.event.dto;

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
public class RequestProductCategorySaveDto {
    @Schema(example = "coffee")
    @NotNull
    private String name;

    @Schema(example = "Y")
    @NotNull
    @Pattern(regexp = "[YN]")
    private String exposeYn;
}
