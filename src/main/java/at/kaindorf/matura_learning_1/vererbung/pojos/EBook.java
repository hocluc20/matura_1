package at.kaindorf.matura_learning_1.vererbung.pojos;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * EBook - 10.05.2025
 *
 * @author david
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EBook extends Book{
    private String url;
}
