package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    List<Veterinario> findByEspecialidade(String especialidade);
}
