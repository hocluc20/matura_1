package at.kaindorf.matura_learning_1.vererbung.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Author - 10.05.2025
 *
 * @author david
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(AuthorPK.class)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @Id
    private String firstname;

    private String lastname;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id"),
            @JoinColumn(name = "name", referencedColumnName = "lastname")},
            inverseJoinColumns = @JoinColumn(name = "isbn")
    )
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(EBook.class),
            @JsonSubTypes.Type(AudioBook.class)
    })
    private Set<Book> books = new HashSet<>();
}
