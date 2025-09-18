package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Alimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {

    List<Alimentacao> findByTipoComida(String tipoComida);
    List<Alimentacao> findByAnimalId(Long animalId);
}
