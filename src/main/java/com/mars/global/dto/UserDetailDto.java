package com.mars.global.dto;

import com.mars.global.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailDto {
    private Integer userId;
    private UserRole role;
}
