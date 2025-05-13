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
public class InitDatabase implements ApplicationRunner{

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initDB();
    }

    private void initDB() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/input.json");
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

        List<Author> authors = om.readerForListOf(Author.class).readValue(is);

        Set<Book> books = authors.stream().flatMap(author -> author.getBooks().stream()).collect(Collectors.toSet());
        Set<Publisher> publishers = authors.stream().flatMap(author -> author.getBooks().stream())
                        .map(Book::getPublisher).collect(Collectors.toSet());

        authors.forEach(author -> {
            Set<Book> newBookSet = new HashSet<>();
            author.getBooks().forEach(book -> {
                Book uniqueBook = books.stream().filter(book1 -> book1.equals(book)).findFirst().orElse(null);
                Publisher uniquePublisher = publishers.stream().filter(publisher -> publisher.equals(uniqueBook.getPublisher())).findFirst().orElse(null);

                uniqueBook.getAuthors().add(author);
                uniqueBook.setPublisher(uniquePublisher);
                uniquePublisher.getBooks().add(uniqueBook);
                newBookSet.add(uniqueBook);
            });
            author.setBooks(newBookSet);
        });

        System.out.println("Simon ozwickt");
        bookRepository.saveAll(books);
    }
}
