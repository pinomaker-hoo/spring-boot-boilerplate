package payhere.cafeproduct.api.productCategory.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductCategoryDetail {
    private Integer id;
    private String name;
    private String exposeYn;
    private Integer orderId;
    private LocalDateTime createdDate;
}
