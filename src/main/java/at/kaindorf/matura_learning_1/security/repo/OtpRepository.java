package at.kaindorf.matura_learning_1.security.repo;

import at.kaindorf.matura_learning_1.security.pojos.OtpToken;
import at.kaindorf.matura_learning_1.security.pojos.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findOtpTokenByUser_Username(String userUsername);

    @Transactional
    void deleteAllByUser(User user);

}
