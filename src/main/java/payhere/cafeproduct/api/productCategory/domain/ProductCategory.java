package payhere.cafeproduct.api.productCategory.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.global.domain.BaseTimeEntity;

@Entity
@Table(name = "TB_PRODUCT_CATEGORY")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("정렬 순서")
    @Column(nullable = false, name = "order_id")
    private Integer orderId;

    @Comment("비밀번호")
    @Column(length = 150, nullable = false)
    private String password;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
