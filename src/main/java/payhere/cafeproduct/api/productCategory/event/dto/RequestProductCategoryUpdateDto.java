package payhere.cafeproduct.api.productCategory.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RequestProductCategoryUpdateDto {
    @Schema(example = "1")
    @NotNull
    private Integer productCategoryId;

    @Schema(example = "coffee")
    @NotNull
    private String name;

    @Schema(example = "Y")
    @NotNull
    @Pattern(regexp = "[YN]")
    private String exposeYn;
}
