package payhere.cafeproduct.api.user.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUser {
    private Integer id;
    private String phoneNumber;
    private String password;
}