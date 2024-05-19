package beaverbackend.jpa.repository;

import beaverbackend.jpa.model.AppUser;
import beaverbackend.jpa.model.BasicAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicAuthUserRepository extends JpaRepository<BasicAuthUser, Long> {
    public Optional<BasicAuthUser> findByUser(AppUser user);

}
