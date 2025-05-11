package at.kaindorf.matura_learning_1.vererbung.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * AudioBook - 10.05.2025
 *
 * @author david
 */

public class AudioBook extends Book{

    private String length;
    private String category;
}
