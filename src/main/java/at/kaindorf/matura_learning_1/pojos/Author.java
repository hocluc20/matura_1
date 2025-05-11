package at.kaindorf.matura_learning_1.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)

@IdClass(AuthorPK.class)
public class Author {

    @Id
    private Long id;

    @Id
    private String lastname;
//    @EmbeddedId
//    private AuthorPK authorPK;

    @Column(length = 80)
    private String firstname;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name= "author", referencedColumnName = "id"),@JoinColumn(name = "name", referencedColumnName = "firstname")},
            inverseJoinColumns = @JoinColumn(name="book")
    )
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(EBook.class),
            @JsonSubTypes.Type(AudioBook.class)
    })
    private Set<Book> books = new HashSet<>();
}
