package at.kaindorf.matura_learning_1.vererbung.service;

import at.kaindorf.matura_learning_1.vererbung.dto.BookPatchDto;
import at.kaindorf.matura_learning_1.vererbung.pojos.Book;
import at.kaindorf.matura_learning_1.vererbung.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * BookService - 11.05.2025
 *
 * @author david
 */

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book editBook(String isbn, BookPatchDto bookPatchDto){
        Optional<Book> optionalBookInDb =  bookRepository.findBookByIsbn(isbn);
        if (optionalBookInDb.isEmpty()){
            return null;
        }

        Book bookInDb = optionalBookInDb.get();


        // Loop through each field in the Event class
//        for (Field field : Event.class.getDeclaredFields()) {
//            field.setAccessible(true); // Make private fields accessible
//            if(field.getName() != "eventId" && field.getName() != "eventType") {
//                try {
//                    Object updatedValue = field.get(eventUpdates);
//                    if (updatedValue != null) {  // Only update non-null values
//                        field.set(existingEvent, updatedValue);
//                    }
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException("Could not update field " + field.getName(), e);
//                }
//            }
//        }

        for (Field field : bookPatchDto.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                Object value = field.get(bookPatchDto);
                if(value != null){
                    Field bookField = Book.class.getDeclaredField(field.getName());
                    bookField.setAccessible(true);
                    bookField.set(bookInDb, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                // leck eier
            }
        }

        System.out.println(bookInDb);
        return bookRepository.save(bookInDb);

    }
}
