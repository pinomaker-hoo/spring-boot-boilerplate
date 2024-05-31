package com.pinomaker.api.user.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUser {
    private Integer id;
    private String username;
    private String password;
}
