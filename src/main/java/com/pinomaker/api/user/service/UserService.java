package com.pinomaker.api.user.service;

import com.pinomaker.api.user.event.dto.RequestUserLoginDto;
import org.springframework.http.ResponseEntity;
import com.pinomaker.api.user.event.dto.RequestTokenReissueDto;
import com.pinomaker.api.user.event.dto.RequestUserSaveDto;

public interface UserService {
    ResponseEntity<?> saveUser(RequestUserSaveDto dto) throws Exception;

    ResponseEntity<?> loginUser(RequestUserLoginDto dto) throws Exception;

    ResponseEntity<?> reissueToken(RequestTokenReissueDto dto) throws Exception;
}
