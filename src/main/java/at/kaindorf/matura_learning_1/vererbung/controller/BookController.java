package at.kaindorf.matura_learning_1.vererbung.controller;

import at.kaindorf.matura_learning_1.vererbung.dto.BookPatchDto;
import at.kaindorf.matura_learning_1.vererbung.pojos.Book;
import at.kaindorf.matura_learning_1.vererbung.service.BookService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * BookController - 11.05.2025
 *
 * @author david
 */

@RestController
@RequestMapping("/api/data/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PatchMapping("/{isbn}")
    public ResponseEntity<Book> editBook(@PathVariable("isbn") String isbn, @RequestBody BookPatchDto bookPatchDto){
        Book book = bookService.editBook(isbn, bookPatchDto);
        return ResponseEntity.ok(book);
    }
}
