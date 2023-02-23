package com.example.telegrambot.user;

import com.example.telegrambot.configuration.TokenUtil;
import com.example.telegrambot.configuration.UserDetailsImpl;
import com.example.telegrambot.exceptions.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
@Service
public class UserServiceImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;

    @Value("${app.jjwt.tokenType}")
    private String tokenType;
    @Value("${app.jjwt.access_expiration}")
    private String accessExpirationTime;
    @Value("${app.jjwt.refresh_expiration}")
    private String refreshExpirationTime;

    private static final Logger logger = LogManager.getLogger();

    public UserServiceImpl(PasswordEncoder passwordEncoder, TokenUtil tokenUtil, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        this.userRepository = userRepository;
    }
    private AuthResponce createAuthResponse(UserEntity userEntity) {
        UserDetailsImpl userDetails = generateUserDetails(userEntity);
        String accessToken = tokenUtil.generateAccessToken(userDetails);
        String refreshToken = tokenUtil.generateRefreshToken(userDetails);
        return new AuthResponce(accessToken, refreshToken, tokenType, Long.valueOf(accessExpirationTime), Long.valueOf(refreshExpirationTime));
    }
    private UserDetailsImpl generateUserDetails(UserEntity userEntity) {
        return new UserDetailsImpl(userEntity);
    }

    @Override
    public UserEntity getUser(String username) {
        logger.info(" user found at {}", username);
        if (userRepository.findByUsername(username).isEmpty())
            throw new DataNotFoundException("User is not in our db");
        return userRepository.findByUsername(username).get();
    }

    @Override
    public List<HashMap<String, Object>> getAllUsers() {
        logger.info("Fetching all users");
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (UserEntity user : userRepository.findAll()) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("id", user.getId());
            res.put("username", user.getUsername());
            res.put("status", user.getStatus());
            result.add(res);
        }
        return result;
    }

    @Override
    public AuthResponce signIn(SignInRequest signInRequest) throws Exception {
        Optional<UserEntity> optional = userRepository.findByUsername(signInRequest.getUsername());
        if (optional.isPresent()) {
            if (!optional.get().getStatus().equals(DataStatusEnum.ACTIVE)) {
                throw new UserNotActivatedException(signInRequest.getUsername());
            }
            if (!passwordEncoder.matches(signInRequest.getPassword(), optional.get().getPassword())) {
                throw new BadCredentialsException(signInRequest.getUsername());
            }
            return createAuthResponse(optional.get());
        }else {
            throw new BadCredentialsException(signInRequest.getUsername());
        }
    }

    @Override
    public AuthResponce signUp(SignUpRequest signUpRequest) throws NoSuchAlgorithmException {
        Optional<UserEntity> optional = userRepository.findByUsername(signUpRequest.getUsername());
        if (optional.isPresent()) {
            throw new UserAlreadyExistException("name_is_already_exists");
        }
        UserEntity userEntity = saveUser(signUpRequest.getUsername(), signUpRequest.getFullName(), signUpRequest.getPassword(), signUpRequest.getStatus(), signUpRequest.getRole());

        logger.info("new user is created in our table the username is {}", signUpRequest.getUsername());
        return createAuthResponse(userEntity);
    }
    private UserEntity saveUser(String username, String fullName, String password, DataStatusEnum active, RoleNameEnum role) throws NoSuchAlgorithmException {
        logger.info("User save {}", username);
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(username);
        userEntity.setFullName(fullName);

        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setHashPassword(generateMD5Hash(password));
        userEntity.setRole(role);
        userEntity.setStatus(active);
        return userRepository.save(userEntity);
    }
    private String generateMD5Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String md5Hex = DatatypeConverter.printHexBinary(digest).toUpperCase(Locale.ROOT);
        return md5Hex;
    }

    @Override
    public UserEntity findById(UUID userId) {
        UserEntity user = userRepository.findById(userId).get();
        if (!user.getStatus().equals(DataStatusEnum.ACTIVE))
            throw new DataNotFoundException("User not found in our Db");
        return user;
    }

    @Override
    public void logout(TokensRequest tokensRequest) {
        tokenUtil.revokeTokens(tokensRequest.getAccessToken(), tokensRequest.getRefreshToken());
    }

    @Override
    public UserEntity changeStatus(UUID id, UserRequest request) {
        return null;
    }
}
