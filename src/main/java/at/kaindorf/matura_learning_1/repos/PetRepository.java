package at.kaindorf.matura_learning_1.repos;


import at.kaindorf.matura_learning_1.pojos.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author: hocluc20
 * date: 11/05/2025
 * project: matura_learning_1
 * package_name: at.kaindorf.matura_learning_1.repos
 **/
public interface PetRepository extends JpaRepository<Pet, Long> {
}
