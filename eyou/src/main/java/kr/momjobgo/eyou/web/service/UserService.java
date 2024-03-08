package kr.momjobgo.eyou.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.momjobgo.eyou.web.dto.Token;
import kr.momjobgo.eyou.web.jpa.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUser();

    Token.TokenResponse login(Token.TokenRequest request) throws JsonProcessingException;
}
