package com.example.telegrambot.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final Logger logger = LogManager.getLogger();

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/sign_in")
    public ResponseEntity<AuthResponce> signIn(@RequestBody SignInRequest signInRequest, HttpServletRequest request) throws Exception {
        logger.debug("request = " + request.getRemoteAddr());
        logger.debug("request = " + request.getRemoteHost());
        logger.debug("request = " + request.getLocalAddr());
          return ResponseEntity.ok(userService.signIn(signInRequest));
    }
//    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    @PostMapping("/sign_up")
    public ResponseEntity<AuthResponce> signUp(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) throws NoSuchAlgorithmException {
        logger.debug("request = " + request.getRemoteAddr());
        logger.debug("request = " + request.getRemoteHost());
        logger.debug("request = " + request.getLocalAddr());
        return ResponseEntity.ok(userService.signUp(signUpRequest));

    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokensRequest tokensRequest, HttpServletRequest request){
        userService.logout(tokensRequest);
        return ResponseEntity.ok(null);
    }

}
