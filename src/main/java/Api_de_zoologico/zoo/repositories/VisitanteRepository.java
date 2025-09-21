package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    List<Visitante> findByNomeContainingIgnoreCase(String nome);
    List<Visitante> findByCpfContaining(String cpf);

    Optional<Visitante> findByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);

    List<Visitante> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT COUNT(v) FROM Visitante v WHERE v.cpf = ?1")
    long countByCpf(String cpf);
}