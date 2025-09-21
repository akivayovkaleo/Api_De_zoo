package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByIdadeBetween(int idadeMin, int idadeMax);
    List<Animal> findByNomeContainingIgnoreCase(String nome);
    List<Animal> findByEspecieNomeContainingIgnoreCase(String nome);

    // Conta animais por habitat
    @Query("SELECT COUNT(a) FROM Animal a WHERE a.habitat.id = ?1")
    long countByHabitatId(Long habitatId);

    // Verifica se animal tem cuidador
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Animal a WHERE a.id = ?1 AND a.cuidador IS NOT NULL")
    boolean existsByIdAndCuidadorIsNotNull(Long id);
}