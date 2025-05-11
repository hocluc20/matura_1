package at.kaindorf.matura_learning_1.security.controller;

import at.kaindorf.matura_learning_1.vererbung.pojos.Book;
import at.kaindorf.matura_learning_1.security.services.DataAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DataAccessController - 11.05.2025
 *
 * @author david
 */

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
@Slf4j
public class DataAccessController {

    private final DataAccessService dataAccessService;

    @GetMapping("/books/all")
    public ResponseEntity<Iterable<Book>> getAllBooks(@RequestParam(value = "type", required = true, defaultValue = "all") String type){
        List<Book> books = dataAccessService.getAllBooks(type);
        //log.info(books.toString());
        return ResponseEntity.ok(books);
    }
}
