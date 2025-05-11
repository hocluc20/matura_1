package at.kaindorf.matura_learning_1.controller;

import at.kaindorf.matura_learning_1.pojos.Author;
import at.kaindorf.matura_learning_1.pojos.Book;
import at.kaindorf.matura_learning_1.services.DataAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.controller
 **/

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class DataAccessController {

    private final DataAccessService dataAccessService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks (@RequestParam(value = "type", required = true, defaultValue = "all") String type){
        return ResponseEntity.status(200).body(dataAccessService.getAllBooks(type));
    }
}
