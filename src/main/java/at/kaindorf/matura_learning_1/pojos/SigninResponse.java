package at.kaindorf.matura_learning_1.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.pojos
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SigninResponse {
    private String mfa;
    private Integer otp;
}
