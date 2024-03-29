package iiqcov.blog.springbootdeveloper.repository;

import iiqcov.blog.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByipAddress(String ipAddress);
}
