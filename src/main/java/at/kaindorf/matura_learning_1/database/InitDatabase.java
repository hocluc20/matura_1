package at.kaindorf.matura_learning_1.database;

import at.kaindorf.matura_learning_1.pojos.*;
import at.kaindorf.matura_learning_1.repos.AuthorRepository;
import at.kaindorf.matura_learning_1.repos.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Column;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
    author: hocluc20
    date: 10/05/2025 
    project: matura_learning_1
    package_name: at.kaindorf.matura_learning_1.database
**/

@Slf4j
@RequiredArgsConstructor
@Component
public class InitDatabase implements ApplicationRunner {

    private final AuthorRepository authorRepository;

    private final PetRepository petRepository;

    public void init(){
        InputStream is = getClass().getResourceAsStream("/input.json");
        ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());


        log.info("Reading");
        try {
            List<Author> authors = om.readerForListOf(Author.class).readValue(is);

            Set<Book> books = authors.stream().flatMap(author -> author.getBooks().stream()).collect(Collectors.toCollection(HashSet::new));

            Set<Publisher> publishers = authors.stream()
                    .flatMap(author -> author.getBooks().stream())
                    .map(Book::getPublisher).collect(Collectors.toCollection(HashSet::new));

            authors.forEach(author -> {
                Set<Book> authorBooks = new HashSet<>();
                author.getBooks().forEach(book -> {
                    Book bookOfAuthor = books.stream().filter(book1 -> book1.equals(book)).findFirst().get();
                    book.setPublisher(publishers.stream().filter(publisher -> publisher.equals(book.getPublisher())).findFirst().get());
                    authorBooks.add(bookOfAuthor);
                });
                author.setBooks(authorBooks);
            });




            System.out.println(authors);

            authorRepository.saveAll(authors);



            InputStream xmlis = getClass().getResourceAsStream("/pets.xml");
            List<Pet> pets = JAXB.unmarshal(xmlis, PetHolder.class).getPets();
            petRepository.saveAll(pets);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }





    @Override
    public void run(ApplicationArguments args) throws Exception {
        init();
    }

}
