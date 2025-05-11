package at.kaindorf.matura_learning_1.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SignInResponse - 11.05.2025
 *
 * @author david
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponse {
    private String mfaToken;
    private Integer otpToken;
}
