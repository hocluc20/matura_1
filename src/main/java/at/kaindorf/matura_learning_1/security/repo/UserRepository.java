package at.kaindorf.matura_learning_1.security.repo;

import at.kaindorf.matura_learning_1.security.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
