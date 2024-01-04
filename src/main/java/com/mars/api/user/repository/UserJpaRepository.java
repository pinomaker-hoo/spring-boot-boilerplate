package com.mars.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mars.api.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Integer>, UserJpaCustomRepository {
}
