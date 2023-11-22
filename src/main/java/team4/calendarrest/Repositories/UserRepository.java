package team4.calendarrest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team4.calendarrest.enteties.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}