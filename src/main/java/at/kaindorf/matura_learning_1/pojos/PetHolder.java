package at.kaindorf.matura_learning_1.pojos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.pojos
 **/



@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "pets")
@XmlAccessorType(XmlAccessType.FIELD)
public class PetHolder {

    @XmlElement(name = "pet")
    private List<Pet> pets = new ArrayList<>();
}
