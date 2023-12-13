package payhere.cafeproduct.api.productCategory.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCategoryWithUserId {
    private Integer id;
    private Integer userId;
}
