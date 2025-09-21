package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {
    List<Especie> findByNomeContainingIgnoreCase(String nome);
    List<Especie> findByFamiliaContainingIgnoreCase(String familia);
    List<Especie> findByOrdemContainingIgnoreCase(String ordem);
    List<Especie> findByClasseContainingIgnoreCase(String classe);

    Optional<Especie> findByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, Long id);

    @Query("SELECT COUNT(e) FROM Especie e WHERE e.familia = ?1")
    long countByFamilia(String familia);
}