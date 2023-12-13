package payhere.cafeproduct.api.productCategory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.global.domain.BaseTimeEntity;

@Entity
@Table(name = "TB_PRODUCT_CATEGORY")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("카테고리 이름")
    @Column(length = 30, nullable = false)
    private String name;

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
