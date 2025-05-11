package at.kaindorf.matura_learning_1.vererbung.db;

import at.kaindorf.matura_learning_1.vererbung.pojos.Author;
import at.kaindorf.matura_learning_1.vererbung.pojos.Book;
import at.kaindorf.matura_learning_1.vererbung.pojos.Publisher;
import at.kaindorf.matura_learning_1.vererbung.repository.AuthorRepository;
import at.kaindorf.matura_learning_1.vererbung.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * InitDatabase - 10.05.2025
 *
 * @author david
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDatabase implements ApplicationRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        init();
    }

    private void init(){
        InputStream is = this.getClass().getResourceAsStream("/input.json");
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

        Set<Book> books = new HashSet<>();
        Set<Publisher> publishers = new HashSet<>();

        try {
            List<Author> authors = om.readerForListOf(Author.class).readValue(is);

            books = authors.stream()
                    .flatMap(author -> author.getBooks().stream())
                            .collect(Collectors.toSet());

            publishers = authors.stream()
                    .flatMap(author -> author.getBooks().stream())
                    .map(Book::getPublisher).collect(Collectors.toSet());

            Set<Book> finalBooks = books;
            Set<Publisher> finalPublishers = publishers;

            authors.forEach(author -> {
                HashSet<Book> books1 = new HashSet<>();
                author.getBooks().forEach(book -> {
                    Book bookUnique = finalBooks.stream().filter(book1 -> book1.equals(book)).findFirst().orElse(null);
                    bookUnique.getAuthors().add(author);
                    books1.add(bookUnique);

                    Publisher publisherUnique = finalPublishers.stream().filter(publisher1 -> publisher1.equals(bookUnique.getPublisher())).findFirst().orElse(null);
                    bookUnique.setPublisher(publisherUnique);
                    publisherUnique.getBooks().add(bookUnique);
                });
                author.setBooks(books1);
            });

            bookRepository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
