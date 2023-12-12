package payhere.cafeproduct.api.product.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductWithUserId {
    private Long id;
    private Integer userId;
}
