package payhere.cafeproduct.api.adminLog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import payhere.cafeproduct.api.user.domain.User;
import payhere.cafeproduct.global.domain.BaseTimeCreatedEntity;
import payhere.cafeproduct.global.enums.LogType;

@Entity
@Table(name = "TB_ADMIN_LOG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLog extends BaseTimeCreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("로그 종류")
    @Column(nullable = false, name = "log_type")
    private LogType logType;

    @Comment("로그 내용")
    @Column(nullable = false, name = "log", columnDefinition = "TEXT")
    private String log;

    @Comment("로그 데이터")
    @Column(nullable = false, name = "log_data", length = 50)
    private String logData;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
