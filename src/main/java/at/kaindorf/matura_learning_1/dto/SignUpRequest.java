package at.kaindorf.matura_learning_1.dto;

import lombok.Data;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.dto
 **/
@Data
public class SignUpRequest {
    private String username;
    private String password;
}
