package at.kaindorf.matura_learning_1.repos;


import at.kaindorf.matura_learning_1.pojos.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.repos
 **/
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM EBook b ORDER BY b.isbn")
    List<Book> findAllEBooks();

    @Query("SELECT b FROM AudioBook b ORDER BY b.price DESC, b.isbn")
    List<Book> findAllAudioBooks();

    List<Book> findAllByOrderByPriceDescIsbnAsc();
}
