package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    boolean existsByDocumento(String documento);
}