package at.kaindorf.matura_learning_1.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedEntityGraph;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
    author: hocluc20
    date: 10/05/2025 
    project: matura_learning_1
    package_name: at.kaindorf.matura_learning_1.pojos
**/

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)

public class EBook extends Book{
    private String url;
}
