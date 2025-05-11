package at.kaindorf.matura_learning_1.pojos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Publisher {

    @Id
    @JsonIgnore
    @SequenceGenerator(name = "dave", initialValue = 3, allocationSize = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dave")
    private Long id;

    @Column(length = 80, name = "name")
    @JsonAlias("name")
    private String name;

    private String url;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

}
