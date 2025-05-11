package at.kaindorf.matura_learning_1.security.dto;

import lombok.Data;

/**
 * SignUpRequest - 10.05.2025
 *
 * @author david
 */

@Data
public class SignUpRequest {
    private String username;
    private String password;
}
