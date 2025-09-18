package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByIdadeBetween(int idadeMin, int idadeMax);
    List<Animal> findByNomeContainingIgnoreCase(String nome);
    List<Animal> findByEspecie_NomeIgnoreCase(String nome);
}
