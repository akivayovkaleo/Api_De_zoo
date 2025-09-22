package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Cuidador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuidadorRepository extends JpaRepository<Cuidador, Long> {
    List<Cuidador> findByEspecialidade(String especialidade);
    List<Cuidador> findByTurno(String turno);
    Optional<Cuidador> findByEmail(String email);
}
