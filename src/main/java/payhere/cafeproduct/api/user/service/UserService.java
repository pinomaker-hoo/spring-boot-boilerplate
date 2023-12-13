package payhere.cafeproduct.api.user.service;

import org.springframework.http.ResponseEntity;
import payhere.cafeproduct.api.user.event.dto.RequestTokenReissueDto;
import payhere.cafeproduct.api.user.event.dto.RequestUserLoginDto;
import payhere.cafeproduct.api.user.event.dto.RequestUserSaveDto;

public interface UserService {
    ResponseEntity<?> saveUser(RequestUserSaveDto dto) throws Exception;

    ResponseEntity<?> loginUser(RequestUserLoginDto dto) throws Exception;

    ResponseEntity<?> reissueToken(RequestTokenReissueDto dto) throws Exception;
}
