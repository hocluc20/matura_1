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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(AuthorPK.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private long id;

    @Column(length = 80)
    @EqualsAndHashCode.Include
    private String firstname;

    @Column(length = 80)
    @EqualsAndHashCode.Include
    private String lastname;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            inverseJoinColumns = @JoinColumn(name = "book"),
            joinColumns = {@JoinColumn(name = "author", referencedColumnName = "id"), @JoinColumn(name = "name", referencedColumnName = "firstname")}
    )
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(EBook.class),
            @JsonSubTypes.Type(AudioBook.class)
    })
    @EqualsAndHashCode.Exclude
    private Set<Book> books = new HashSet<>();
}
