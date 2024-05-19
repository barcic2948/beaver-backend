package beaverbackend.service.auth;

import beaverbackend.controllers.common.BadRequestException;
import org.springframework.security.core.Authentication;

public interface OAuth2LoginService {

    public Authentication processGithubOauth2Login(String code) throws BadRequestException;

}
