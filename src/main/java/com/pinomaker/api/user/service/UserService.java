package com.pinomaker.api.user.service;

import com.pinomaker.api.user.event.dto.RequestUserLoginDto;
import org.springframework.http.ResponseEntity;
import com.pinomaker.api.user.event.dto.RequestTokenReissueDto;
import com.pinomaker.api.user.event.dto.RequestUserSaveDto;

public interface UserService {
    ResponseEntity<?> saveUser(RequestUserSaveDto dto);

    ResponseEntity<?> loginUser(RequestUserLoginDto dto);

    ResponseEntity<?> reissueToken(RequestTokenReissueDto dto);
}
