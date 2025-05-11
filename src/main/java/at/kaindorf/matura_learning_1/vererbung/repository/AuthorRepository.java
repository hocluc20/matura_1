package at.kaindorf.matura_learning_1.vererbung.repository;

import at.kaindorf.matura_learning_1.vererbung.pojos.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
