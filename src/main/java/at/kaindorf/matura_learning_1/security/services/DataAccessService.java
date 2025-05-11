package at.kaindorf.matura_learning_1.security.services;

import at.kaindorf.matura_learning_1.vererbung.pojos.Book;
import at.kaindorf.matura_learning_1.vererbung.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DataAccessService - 11.05.2025
 *
 * @author david
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class DataAccessService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks(String type){
        if(type.equalsIgnoreCase("e")){
            return bookRepository.findAllEBooks();
        } else if (type.equalsIgnoreCase("audio")) {
            return bookRepository.findAllAudioBooks();
        } else {
            return bookRepository.findAllByOrderByPriceDescIsbnAsc();
        }
    }
}
