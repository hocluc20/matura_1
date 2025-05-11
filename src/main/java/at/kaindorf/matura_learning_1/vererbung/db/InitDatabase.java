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
public class InitDatabase {

}
