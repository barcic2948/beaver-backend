package beaverbackend.service;

import beaverbackend.jpa.model.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findByEmail(String email);

}
