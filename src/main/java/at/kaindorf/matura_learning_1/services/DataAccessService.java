package at.kaindorf.matura_learning_1.services;

import at.kaindorf.matura_learning_1.pojos.Author;
import at.kaindorf.matura_learning_1.pojos.Book;
import at.kaindorf.matura_learning_1.repos.AuthorRepository;
import at.kaindorf.matura_learning_1.repos.BookRepository;
import at.kaindorf.matura_learning_1.repos.UserRepository;
import at.kaindorf.matura_learning_1.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.services
 **/


@Service
@RequiredArgsConstructor
@Slf4j
public class DataAccessService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks(String type){
       if(type.equals("e")){
           log.info("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
           return bookRepository.findAllEBooks();
       } else if (type.equals("audio")) {
           log.info("AUDIOOOOOOOOOOOOOO");
           return bookRepository.findAllAudioBooks();
       } else {
           log.info("ALLLLLLLLL");
            return bookRepository.findAllByOrderByPriceDescIsbnAsc();
       }
    }
}
