package at.kaindorf.matura_learning_1.repos;

import at.kaindorf.matura_learning_1.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.repos
 **/

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
