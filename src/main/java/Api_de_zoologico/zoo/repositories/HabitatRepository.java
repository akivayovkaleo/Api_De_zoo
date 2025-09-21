package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {
    List<Habitat> findByTipoContainingIgnoreCase(String tipo);

    @Query("SELECT COUNT(h) FROM Habitat h WHERE h.tipo = ?1")
    long countByTipo(String tipo);

    List<Habitat> findByNomeContainingIgnoreCase(String nome);
}