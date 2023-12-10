package payhere.cafeproduct.global.dto;

import lombok.Builder;
import lombok.Data;
import payhere.cafeproduct.global.enums.UserRole;

@Data
@Builder
public class UserDetailDto {
    private Long userId;
    private UserRole role;
}