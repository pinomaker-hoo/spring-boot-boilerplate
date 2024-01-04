package com.mars.api.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import com.mars.global.domain.BaseTimeEntity;

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

    @Comment("아이디")
    @Column(length = 30, unique = true, nullable = false, name = "phone_number")
    private String username;

    @Comment("비밀번호")
    @Column(length = 150, nullable = false)
    private String password;
}
