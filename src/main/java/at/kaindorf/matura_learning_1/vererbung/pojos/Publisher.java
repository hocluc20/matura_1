package at.kaindorf.matura_learning_1.vererbung.pojos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Publisher - 10.05.2025
 *
 * @author david
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private long id;

    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private String url;

    @OneToMany(mappedBy = "publisher")
    @JsonManagedReference
    private Set<Book> books = new HashSet<>();

}