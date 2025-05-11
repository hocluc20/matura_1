package at.kaindorf.matura_learning_1.vererbung.repository;

import at.kaindorf.matura_learning_1.vererbung.pojos.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM Book b WHERE TYPE(b) = EBook ORDER BY b.price DESC, b.isbn ASC")
    List<Book> findAllEBooks();

    @Query("SELECT b FROM Book b WHERE TYPE (b) = AudioBook ORDER BY b.price DESC, b.isbn ASC")
    List<Book> findAllAudioBooks();

    List<Book> findAllByOrderByPriceDescIsbnAsc();

    Optional<Book> findBookByIsbn(String isbn);
}
