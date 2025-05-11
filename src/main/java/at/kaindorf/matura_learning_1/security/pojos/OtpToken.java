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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OtpToken {
    @Id
    @GeneratedValue
    private Long id;

    private Integer otpCode;

    @OneToOne(cascade = CascadeType.MERGE)
    private User user;
}
