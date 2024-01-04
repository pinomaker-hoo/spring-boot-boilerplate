package com.mars.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.sql.Timestamp;

@Entity
@Table(name = "shedlock")
public class Shedlock {
    @Id
    @Comment("스케줄잠금 name")
    @Column(length = 64, name = "name")
    private String name;

    @Comment("잠금기간")
    @Column(nullable = false, name = "lock_until")
    private Timestamp lockUntil;

    @Comment("잠금일시")
    @Column(nullable = false, name = "locked_at")
    private Timestamp lockedAt;

    @Comment("잠금신청자")
    @Column(nullable = false, length = 255, name = "locked_by")
    private String lockedBy;
}
