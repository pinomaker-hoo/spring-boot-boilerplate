package payhere.cafeproduct.api.log.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import payhere.cafeproduct.global.domain.BaseTimeCreatedEntity;
import payhere.cafeproduct.global.enums.LogType;

@Entity
@Table(name = "TB_LOG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log extends BaseTimeCreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("로그 종류")
    @Column(nullable = false, name = "log_type")
    private LogType logType;

    @Comment("로그 내용")
    @Column(nullable = false, name = "log", length = 50)
    private String log;

    @Comment("로그 데이터")
    @Column(nullable = true, name = "log_data", columnDefinition = "TEXT")
    private String logData;

    @Comment("유저 ID")
    @Column(nullable = false, name = "user_id")
    private Integer userId;
}
