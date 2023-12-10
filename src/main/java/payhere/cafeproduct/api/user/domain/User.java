package payhere.cafeproduct.api.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import payhere.cafeproduct.global.domain.BaseTimeEntity;

@Entity
@Table(name = "TB_USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("전화번호")
    @Column(length = 15, unique = true, nullable = false, name = "phone_number")
    private String phoneNumber;

    @Comment("비밀번호")
    @Column(length = 150, nullable = false)
    private String password;
}
