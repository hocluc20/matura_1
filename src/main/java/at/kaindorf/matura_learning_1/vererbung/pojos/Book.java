package at.kaindorf.matura_learning_1.vererbung.pojos;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Book - 10.05.2025
 *
 * @author david
 */

@Entity
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private String isbn;

    @EqualsAndHashCode.Include
    private Double price;

    @EqualsAndHashCode.Include
    @JsonAlias("publishing_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @EqualsAndHashCode.Include
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    @JoinColumn(name = "publisher")
    private Publisher publisher;

    @ToString.Exclude
    @ManyToMany(mappedBy = "books", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Author> authors = new HashSet<>();
}
