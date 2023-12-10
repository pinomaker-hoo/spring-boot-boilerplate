package payhere.cafeproduct.api.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import payhere.cafeproduct.api.productCategory.domain.ProductCategory;
import payhere.cafeproduct.global.domain.BaseTimeEntity;
import payhere.cafeproduct.global.enums.ProductSize;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PRODUCT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("가격")
    @Column(nullable = false, name = "price")
    private Integer price;

    @Comment("원가")
    @Column(nullable = false, name = "cost")
    private Integer cost;

    @Comment("상품 이름")
    @Column(nullable = false, length = 20, name = "name")
    private String name;

    @Comment("상품 코드(바코드)")
    @Column(nullable = false, length = 30, name = "code")
    private String code;

    @Comment("유통 기한")
    @Column(nullable = false, name = "expiration_date")
    private LocalDateTime expirationDate;

    @Comment("사이즈")
    @Column(nullable = false, name = "size")
    private ProductSize size;

    @Comment("매진 여부")
    @Column(columnDefinition = "CHAR(1)", nullable = false, name = "sold_out_yn")
    private String soldOutYn;

    @Comment("노출 여부")
    @Column(columnDefinition = "CHAR(1)", nullable = false, name = "expose_yn")
    private String exposeYn;

    @Comment("삭제 여부")
    @Column(columnDefinition = "CHAR(1)", nullable = false, name = "del_yn")
    private String delYn;

    @Comment("생성자 ID")
    @Column(nullable = false, name = "created_id")
    private Integer createdId;

    @Comment("수정자 ID")
    @Column(nullable = false, name = "modified_id")
    private Integer modifiedId;

    @Comment("삭제자 ID")
    @Column(nullable = true, name = "deleted_id")
    private Integer deletedId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;
}
