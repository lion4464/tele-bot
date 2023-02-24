package com.example.telegrambot.user;

import com.example.telegrambot.exceptions.DataNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserEntity getUser(String username) throws DataNotFoundException;
    List<HashMap<String,Object>> getAllUsers();
    AuthResponce signIn(SignInRequest signInRequest) throws Exception;
    AuthResponce signUp(SignUpRequest signUpRequest) throws NoSuchAlgorithmException;
    UserEntity findById(UUID userId) throws DataNotFoundException;
    void logout(TokensRequest tokensRequest);
    UserEntity changeStatus(UUID id, UserRequest request);
}
