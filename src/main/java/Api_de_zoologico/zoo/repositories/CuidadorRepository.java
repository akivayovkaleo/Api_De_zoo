package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Cuidador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuidadorRepository extends JpaRepository<Cuidador, Long> {
    List<Cuidador> findByEspecialidadeContainingIgnoreCase(String especialidade);
    List<Cuidador> findByTurnoContainingIgnoreCase(String turno);
    List<Cuidador> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT COUNT(c) FROM Cuidador c WHERE c.especialidade = ?1")
    long countByEspecialidade(String especialidade);

    boolean existsByNomeAndIdNot(String nome, Long id);
}