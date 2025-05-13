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

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OtpToken {
    @Id
    @GeneratedValue
    private Long id;

    private Integer otpCode;

    @ManyToOne
    private User user;
}
