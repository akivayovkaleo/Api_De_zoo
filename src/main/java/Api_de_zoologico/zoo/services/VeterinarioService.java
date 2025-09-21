package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.VeterinarioDto;
import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.repositories.VeterinarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VeterinarioService {
    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<Veterinario> findAll() {
        try {
            return veterinarioRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os veterinários: " + e.getMessage());
        }
    }

    public Veterinario findById(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado com ID: " + id));
    }

    public List<Veterinario> findByEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new RuntimeException("Especialidade não pode ser vazia ou nula");
        }
        try {
            return veterinarioRepository.findByEspecialidadeContainingIgnoreCase(especialidade.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veterinários pela especialidade '" + especialidade + "': " + e.getMessage());
        }
    }

    public List<Veterinario> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome não pode ser vazio ou nulo");
        }
        try {
            return veterinarioRepository.findByNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veterinários pelo nome '" + nome + "': " + e.getMessage());
        }
    }

    public Veterinario create(VeterinarioDto veterinarioDto) {
        try {
            if (veterinarioDto.nome() == null || veterinarioDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do veterinário é obrigatório");
            }

            if (veterinarioDto.crmv() == null || veterinarioDto.crmv().trim().isEmpty()) {
                throw new RuntimeException("CRMV do veterinário é obrigatório");
            }

            if (veterinarioRepository.findByCrmv(veterinarioDto.crmv().trim()).isPresent()) {
                throw new RuntimeException("Já existe um veterinário cadastrado com o CRMV '" + veterinarioDto.crmv().trim() + "'");
            }

            Veterinario veterinario = new Veterinario();
            veterinario.setNome(veterinarioDto.nome().trim());
            veterinario.setCrmv(veterinarioDto.crmv().trim().toUpperCase());
            veterinario.setEspecialidade(veterinarioDto.especialidade() != null ? veterinarioDto.especialidade().trim() : null);

            return veterinarioRepository.save(veterinario);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar veterinário: " + e.getMessage());
        }
    }

    public Veterinario update(Long id, VeterinarioDto veterinarioDto) {
        try {
            Veterinario veterinarioExistente = findById(id);

            if (veterinarioDto.nome() == null || veterinarioDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do veterinário é obrigatório");
            }

            if (veterinarioDto.crmv() == null || veterinarioDto.crmv().trim().isEmpty()) {
                throw new RuntimeException("CRMV do veterinário é obrigatório");
            }

            if (!veterinarioExistente.getCrmv().equals(veterinarioDto.crmv().trim().toUpperCase()) &&
                    veterinarioRepository.existsByCrmvAndIdNot(veterinarioDto.crmv().trim().toUpperCase(), id)) {
                throw new RuntimeException("Já existe um veterinário cadastrado com o CRMV '" + veterinarioDto.crmv().trim() + "'");
            }

            veterinarioExistente.setNome(veterinarioDto.nome().trim());
            veterinarioExistente.setCrmv(veterinarioDto.crmv().trim().toUpperCase());
            veterinarioExistente.setEspecialidade(veterinarioDto.especialidade() != null ? veterinarioDto.especialidade().trim() : null);

            return veterinarioRepository.save(veterinarioExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar veterinário com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            veterinarioRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover veterinário com ID " + id + ": " + e.getMessage());
        }
    }
}