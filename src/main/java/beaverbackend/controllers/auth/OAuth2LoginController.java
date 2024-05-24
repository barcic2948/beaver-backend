package beaverbackend.controllers.auth;

import beaverbackend.service.auth.AuthService;
import beaverbackend.service.auth.OAuth2LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2LoginController {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginController.class);
    private final OAuth2LoginService oAuth2LoginService;
    private final AuthService authService;

    @PostMapping("/login/oauth2/github")
    public ResponseEntity<?> githubLoginCallback(@RequestParam String code, HttpServletResponse response) {
        Authentication authentication = oAuth2LoginService.processGithubOauth2Login(code);
        return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication, response));
    }

}
