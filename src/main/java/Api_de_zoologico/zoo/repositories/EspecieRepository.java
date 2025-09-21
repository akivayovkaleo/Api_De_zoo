package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {
    List<Especie> findByNomeContainingIgnoreCase(String nome);
    Optional<Especie> findByNome(String nome);
    List<Especie> findByFamilia(String familia);
    List<Especie> findByClasse(String classe);
}