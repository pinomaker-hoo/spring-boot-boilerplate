package com.pinomaker.global.dto;

import com.pinomaker.global.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailDto {
    private Integer userId;
    private UserRole role;
}
