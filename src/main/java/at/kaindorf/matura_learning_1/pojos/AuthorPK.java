package at.kaindorf.matura_learning_1.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.pojos
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Embeddable
public class AuthorPK implements Serializable {
//    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(length = 80)
    private String lastname;
}
