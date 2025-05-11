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


public class Publisher {

    private long id;

    private String name;

    private String url;

    private Set<Book> books = new HashSet<>();

}