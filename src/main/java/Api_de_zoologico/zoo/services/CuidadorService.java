package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.CuidadorDto;
import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.models.Funcionario;
import Api_de_zoologico.zoo.repositories.CuidadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CuidadorService {

    private final CuidadorRepository cuidadorRepository;
    private final FuncionarioService funcionarioService;

    public List<Cuidador> findAll() {
        return cuidadorRepository.findAll();
    }

    public Cuidador findById(Long id) {
        return cuidadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuidador não encontrado com ID: " + id));
    }

    public List<Cuidador> findByEspecialidade(String especialidade) {
        return cuidadorRepository.findByEspecialidadeContainingIgnoreCase(especialidade.trim());
    }

    public List<Cuidador> findByTurno(String turno) {
        return cuidadorRepository.findByTurnoContainingIgnoreCase(turno.trim());
    }

    public List<Cuidador> findByNome(String nome) {
        return cuidadorRepository.findByNomeContainingIgnoreCase(nome.trim());
    }

    public Cuidador create(CuidadorDto dto) {
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new RuntimeException("Nome do cuidador é obrigatório");
        }

        // Cria o funcionário associado
        Funcionario funcionario = funcionarioService.criarFuncionario(dto.funcionario());

        Cuidador cuidador = new Cuidador();
        cuidador.setNome(dto.nome().trim());
        cuidador.setEspecialidade(dto.especialidade());
        cuidador.setTurno(dto.turno());
        cuidador.setFuncionario(funcionario);

        return cuidadorRepository.save(cuidador);
    }

    public Cuidador update(Long id, CuidadorDto dto) {
        Cuidador existente = findById(id);

        existente.setNome(dto.nome().trim());
        existente.setEspecialidade(dto.especialidade());
        existente.setTurno(dto.turno());

        // Opcional: atualizar o funcionário também
        // funcionarioService.updateFuncionario(existente.getFuncionario().getId(), dto.funcionario());

        return cuidadorRepository.save(existente);
    }

    public void delete(Long id) {
        Cuidador cuidador = findById(id);

        if (cuidador.getAnimais() != null && !cuidador.getAnimais().isEmpty()) {
            throw new RuntimeException("Não é possível remover o cuidador, pois está responsável por animais.");
        }

        cuidadorRepository.delete(cuidador);
    }
}
