package at.kaindorf.matura_learning_1.vererbung.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * BookPatchDto - 11.05.2025
 *
 * @author david
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPatchDto {
    private Double price;
    private String title;
    private LocalDate publishDate;
}
