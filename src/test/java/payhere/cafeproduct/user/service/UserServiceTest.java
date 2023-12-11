package payhere.cafeproduct.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import payhere.cafeproduct.api.user.repository.UserJpaRepository;
import payhere.cafeproduct.api.user.service.UserService;

@DisplayName("User test")
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Test
    public void 회원가입_전화번호는_중복일_수_없습니다() throws Exception {

    }

    @Test
    public void 회원가입_전화번호_형식이_옳지않습니다() throws Exception {

    }

    @Test
    public void 회원가입_성공했습니다() throws Exception {

    }

    @Test
    public void 로그인_유저를_찾을_수_없습니다() throws Exception {

    }

    @Test
    public void 로그인_비밀번호가_같지_않습니다() throws Exception {

    }

    @Test
    public void 토큰재발급_토큰이_유효하지_않습니다() throws Exception {

    }

    @Test
    public void 토큰재발급_토큰의_만료시간이_지났습니다() throws Exception {

    }
}
