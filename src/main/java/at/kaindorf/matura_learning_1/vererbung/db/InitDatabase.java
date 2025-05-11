package at.kaindorf.matura_learning_1.vererbung.db;

import at.kaindorf.matura_learning_1.vererbung.pojos.Author;
import at.kaindorf.matura_learning_1.vererbung.pojos.Book;
import at.kaindorf.matura_learning_1.vererbung.pojos.Publisher;
import at.kaindorf.matura_learning_1.vererbung.repository.AuthorRepository;
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

            for (Author author : authors){
                Set<Book> newBooks = new HashSet<>();
                for (Book book : author.getBooks()){
                    newBooks.add(books.stream().filter(book1 -> book1.equals(book)).findAny().orElse(null));

                    book.setPublisher(publishers.stream().filter(publisher -> publisher.equals(book.getPublisher())).findAny().orElse(null));

                }
                author.setBooks(newBooks);
            }

            System.out.println(authors);
            authorRepository.saveAll(authors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
