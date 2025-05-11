package at.kaindorf.matura_learning_1.repos;


import at.kaindorf.matura_learning_1.pojos.AudioBook;
import at.kaindorf.matura_learning_1.pojos.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * author: hocluc20
 * date: 10/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.repos
 **/
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAuthorsByFirstname(String firstname);
}
