package at.kaindorf.matura_learning_1.pojos;

import at.kaindorf.matura_learning_1.xml.LocalDateAdapter;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.pojos
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@XmlAccessorType(XmlAccessType.FIELD)
public class Pet {

    @Id
    @GeneratedValue
    private Long petId;
    private String name;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthdate;
    private String type;

    @Transient
    private Integer user;

}
