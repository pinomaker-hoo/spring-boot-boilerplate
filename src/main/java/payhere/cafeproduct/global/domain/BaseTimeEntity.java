package payhere.cafeproduct.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @Comment("생성 시간")
    @Column(nullable = false, name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Comment("수정 시간")
    @Column(nullable = false, name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Comment("삭제 시간")
    @Column(nullable = false, name = "deleted_date")
    private LocalDateTime deletedDate;
}