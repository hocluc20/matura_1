package at.kaindorf.matura_learning_1.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthenticationTokenResponse - 10.05.2025
 *
 * @author david
 */

@Data
@AllArgsConstructor
public class AuthenticationTokenResponse {
    private String token;
}
