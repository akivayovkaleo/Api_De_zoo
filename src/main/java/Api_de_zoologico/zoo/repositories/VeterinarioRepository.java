package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    List<Veterinario> findByEspecialidadeContainingIgnoreCase(String especialidade);
    List<Veterinario> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT COUNT(v) FROM Veterinario v WHERE v.especialidade = ?1")
    long countByEspecialidade(String especialidade);

    Optional<Veterinario> findByCrmv(String crmv);
    boolean existsByCrmvAndIdNot(String crmv, Long id);
    boolean existsByNomeAndIdNot(String nome, Long id);
}