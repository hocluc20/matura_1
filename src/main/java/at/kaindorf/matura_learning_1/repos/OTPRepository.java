package at.kaindorf.matura_learning_1.repos;

import at.kaindorf.matura_learning_1.pojos.OTPToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.repos
 **/

public interface OTPRepository extends JpaRepository<OTPToken, Long> {
    Optional<OTPToken> findOTPTokenByUserUsername(String username);
}
