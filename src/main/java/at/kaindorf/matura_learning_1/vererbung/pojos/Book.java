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

@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = false)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(EBook.class),
        @JsonSubTypes.Type(AudioBook.class),
})
public abstract class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private String isbn;

    @Column(precision = 2)
    private Double price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAlias("publishing_date")
    private LocalDate publishDate;

    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "publisher")
    private Publisher publisher;

    @ManyToMany(mappedBy = "books")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();
}
