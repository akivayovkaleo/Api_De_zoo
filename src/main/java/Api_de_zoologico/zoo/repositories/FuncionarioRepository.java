package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByCpf(String cpf);
}
