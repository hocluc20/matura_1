package at.kaindorf.matura_learning_1.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
    author: hocluc20
    date: 10/05/2025 
    project: matura_learning_1
    package_name: at.kaindorf.matura_learning_1.pojos
**/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audiobook")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
public class AudioBook extends Book{
    private String length;
    private String category;
}
