package com.pinomaker.api.user.repository;


import com.pinomaker.api.user.event.vo.LoginUser;

public interface UserJpaCustomRepository {
    boolean existByUsername(String username);

    LoginUser findUserByUsername(String username);
}
