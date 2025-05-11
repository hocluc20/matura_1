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

public abstract class Book {

    private String isbn;

    private Double price;

    private LocalDate publishDate;

    private String title;

    private Publisher publisher;

    private Set<Author> authors = new HashSet<>();
}
