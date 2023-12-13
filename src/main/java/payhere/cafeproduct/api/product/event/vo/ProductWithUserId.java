package payhere.cafeproduct.api.product.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ProductWithUserId {
    private Long id;
    private Integer userId;
}
