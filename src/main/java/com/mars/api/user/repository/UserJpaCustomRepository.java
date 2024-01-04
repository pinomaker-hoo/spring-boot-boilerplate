package com.mars.api.user.repository;


import com.mars.api.user.event.vo.LoginUser;

public interface UserJpaCustomRepository {
    boolean existByUsername(String username);

    LoginUser findUserByUsername(String username);
}
