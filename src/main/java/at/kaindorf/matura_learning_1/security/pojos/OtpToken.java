package at.kaindorf.matura_learning_1.security.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OtpToken - 11.05.2025
 *
 * @author david
 */

public class OtpToken {
    private Long id;

    private Integer otpCode;

    private User user;
}
