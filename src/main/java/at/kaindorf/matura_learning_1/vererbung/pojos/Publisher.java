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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Publisher {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_publisher", initialValue = 1, allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @JsonIgnore
    private long id;

    @Column(length = 80)
    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private String url;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    @JsonManagedReference
    private Set<Book> books = new HashSet<>();

}