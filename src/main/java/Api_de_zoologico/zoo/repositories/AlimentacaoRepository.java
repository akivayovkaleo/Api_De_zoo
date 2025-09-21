package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Alimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {
    List<Alimentacao> findByTipoComidaContainingIgnoreCase(String tipoComida);
    List<Alimentacao> findByAnimalId(Long animalId);

    @Query("SELECT COUNT(a) FROM Alimentacao a WHERE a.animal.id = ?1")
    long countByAnimalId(Long animalId);

    List<Alimentacao> findByAnimalIdOrderByTipoComidaAsc(Long animalId);
}