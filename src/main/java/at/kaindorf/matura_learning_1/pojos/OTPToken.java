package at.kaindorf.matura_learning_1.pojos;

import jakarta.persistence.*;
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
@Entity
public class OTPToken {

    @Id
    @GeneratedValue
    private Long id;

    private Integer otpCode;

    @ManyToOne
    private User user;
}
