package at.kaindorf.matura_learning_1.vererbung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AuthorPK - 10.05.2025
 *
 * @author david
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorPK implements Serializable{
    private Long id;
    private String firstname;
}
