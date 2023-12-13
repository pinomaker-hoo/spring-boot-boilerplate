package payhere.cafeproduct.api.product.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import payhere.cafeproduct.global.enums.ProductSize;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductDetail {
    private Long id;
    private Integer price;
    private Integer cost;
    private String name;
    private String code;
    private LocalDateTime expirationDate;
    private ProductSize size;
    private String soldOutYn;
    private String exposeYn;
    private String categoryName;
    private LocalDateTime createdDate;
}
