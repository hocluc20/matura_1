package at.kaindorf.matura_learning_1.security.dto;

import lombok.Data;

/**
 * SignInRequest - 10.05.2025
 *
 * @author david
 */

@Data
public class SignInRequest {
    private String username;
    private String password;
}
