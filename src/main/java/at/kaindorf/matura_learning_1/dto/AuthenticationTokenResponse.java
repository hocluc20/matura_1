package at.kaindorf.matura_learning_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.dto
 **/
@Data
@AllArgsConstructor
public class AuthenticationTokenResponse {
    private String token;
}
