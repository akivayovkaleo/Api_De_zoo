package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {
    List<Habitat> findByTipo(String tipo);
}
