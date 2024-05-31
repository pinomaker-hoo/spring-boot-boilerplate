package com.pinomaker.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pinomaker.api.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Integer>, UserJpaCustomRepository {
}
