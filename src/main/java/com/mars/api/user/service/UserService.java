package com.mars.api.user.service;

import com.mars.api.user.event.dto.RequestUserLoginDto;
import org.springframework.http.ResponseEntity;
import com.mars.api.user.event.dto.RequestTokenReissueDto;
import com.mars.api.user.event.dto.RequestUserSaveDto;

public interface UserService {
    ResponseEntity<?> saveUser(RequestUserSaveDto dto) throws Exception;

    ResponseEntity<?> loginUser(RequestUserLoginDto dto) throws Exception;

    ResponseEntity<?> reissueToken(RequestTokenReissueDto dto) throws Exception;
}
