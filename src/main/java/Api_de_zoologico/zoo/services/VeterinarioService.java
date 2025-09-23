package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.VeterinarioDto;
import Api_de_zoologico.zoo.models.Funcionario;
import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.repositories.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final FuncionarioService funcionarioService;

    public List<Veterinario> findAll() {
        return veterinarioRepository.findAll();
    }

    public Veterinario findById(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado com ID: " + id));
    }

    public List<Veterinario> findByEspecialidade(String especialidade) {
        return veterinarioRepository.findByEspecialidadeContainingIgnoreCase(especialidade.trim());
    }

    public List<Veterinario> findByNome(String nome) {
        return veterinarioRepository.findByNomeContainingIgnoreCase(nome.trim());
    }

    public Veterinario create(VeterinarioDto dto) {
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new RuntimeException("Nome do veterinário é obrigatório");
        }
        if (dto.crmv() == null || dto.crmv().isBlank()) {
            throw new RuntimeException("CRMV é obrigatório");
        }

        if (veterinarioRepository.findByCrmv(dto.crmv().trim().toUpperCase()).isPresent()) {
            throw new RuntimeException("Já existe veterinário com CRMV '" + dto.crmv() + "'");
        }

        Funcionario funcionario = funcionarioService.criarFuncionario(dto.funcionario());

        Veterinario veterinario = new Veterinario();
        veterinario.setNome(dto.nome().trim());
        veterinario.setCrmv(dto.crmv().trim().toUpperCase());
        veterinario.setEspecialidade(dto.especialidade());
        veterinario.setFuncionario(funcionario);

        return veterinarioRepository.save(veterinario);
    }

    public Veterinario update(Long id, VeterinarioDto dto) {
        Veterinario existente = findById(id);

        if (!existente.getCrmv().equals(dto.crmv().trim().toUpperCase())
                && veterinarioRepository.existsByCrmvAndIdNot(dto.crmv().trim().toUpperCase(), id)) {
            throw new RuntimeException("Já existe veterinário com CRMV '" + dto.crmv() + "'");
        }

        existente.setNome(dto.nome().trim());
        existente.setCrmv(dto.crmv().trim().toUpperCase());
        existente.setEspecialidade(dto.especialidade());

        return veterinarioRepository.save(existente);
    }

    public void delete(Long id) {
        Veterinario veterinario = findById(id);
        veterinarioRepository.delete(veterinario);
    }
}
