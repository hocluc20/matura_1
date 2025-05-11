package at.kaindorf.matura_learning_1.pojos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.*;

/**
    author: hocluc20
    date: 10/05/2025 
    project: matura_learning_1
    package_name: at.kaindorf.matura_learning_1.pojos
**/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@SuperBuilder
@ToString
public abstract class Book{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String isbn;

    @Column(precision = 2)
    private Double price;

    @JsonAlias("publishing_date")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "US")
    private LocalDate publish_date;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name="publisher")
    private Publisher publisher;


    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

}
