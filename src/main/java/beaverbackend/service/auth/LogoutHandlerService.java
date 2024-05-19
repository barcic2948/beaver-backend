package beaverbackend.service.auth;

import beaverbackend.enums.JwtTokenTypeEnum;
import beaverbackend.jpa.model.RefreshToken;
import beaverbackend.jpa.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutHandlerService implements LogoutHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogoutHandlerService.class);
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!authHeader.startsWith(JwtTokenTypeEnum.BEARER.getHeader())) {
            return;
        }
        final String refreshToken = authHeader.substring(7);

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

        RefreshToken storedRefreshToken = refreshTokenRepository.findByToken(refreshToken)
                .map(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                    return token;
                })
                .orElse(null);
    }
}
