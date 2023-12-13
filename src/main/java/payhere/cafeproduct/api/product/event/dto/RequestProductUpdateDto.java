package payhere.cafeproduct.api.product.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import payhere.cafeproduct.global.enums.ProductSize;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RequestProductUpdateDto {
    @Schema(example = "1")
    @NotNull
    private Long productId;

    @Schema(example = "1")
    @NotNull
    private Integer productCategoryId;

    @Schema(example = "30000")
    @NotNull
    private Integer price;

    @Schema(example = "10000")
    @NotNull
    private Integer cost;

    @Schema(example = "아메리카노")
    @NotNull
    private String name;

    @Schema(example = "ABCD_EF@_123")
    @NotNull
    private String code;

    @Schema(example = "2024-12-31 12:00:00")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "유통 기한의 약식과 맞지 않습니다. YYYY-MM-DD HH:mm:ss")
    @NotNull
    private String expirationDate;

    @Schema(example = "SMALL")
    @NotNull
    private ProductSize productSize;

    @Schema(example = "Y")
    @NotNull
    @Pattern(regexp = "[YN]")
    private String exposeYn;

    @Schema(example = "Y")
    @NotNull
    @Pattern(regexp = "[YN]")
    private String soldOutYn;
}
