package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.CuidadorDto;
import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.repositories.CuidadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CuidadorService {
    private final CuidadorRepository cuidadorRepository;

    public CuidadorService(CuidadorRepository cuidadorRepository) {
        this.cuidadorRepository = cuidadorRepository;
    }

    public List<Cuidador> findAll() {
        try {
            return cuidadorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os cuidadores: " + e.getMessage());
        }
    }

    public Cuidador findById(Long id) {
        return cuidadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuidador não encontrado com ID: " + id));
    }

    public List<Cuidador> findByEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new RuntimeException("Especialidade não pode ser vazia ou nula");
        }
        try {
            return cuidadorRepository.findByEspecialidadeContainingIgnoreCase(especialidade.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cuidadores pela especialidade '" + especialidade + "': " + e.getMessage());
        }
    }

    public List<Cuidador> findByTurno(String turno) {
        if (turno == null || turno.trim().isEmpty()) {
            throw new RuntimeException("Turno não pode ser vazio ou nulo");
        }
        try {
            return cuidadorRepository.findByTurnoContainingIgnoreCase(turno.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cuidadores pelo turno '" + turno + "': " + e.getMessage());
        }
    }

    public List<Cuidador> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome não pode ser vazio ou nulo");
        }
        try {
            return cuidadorRepository.findByNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cuidadores pelo nome '" + nome + "': " + e.getMessage());
        }
    }

    public Cuidador create(CuidadorDto cuidadorDto) {
        try {
            if (cuidadorDto.nome() == null || cuidadorDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do cuidador é obrigatório");
            }


            List<Cuidador> cuidadoresExistentes = cuidadorRepository.findByNomeContainingIgnoreCase(cuidadorDto.nome().trim());
            if (!cuidadoresExistentes.isEmpty()) {
                throw new RuntimeException("Já existe um cuidador com o nome '" + cuidadorDto.nome().trim() + "'");
            }

            Cuidador cuidador = new Cuidador();
            cuidador.setNome(cuidadorDto.nome().trim());
            cuidador.setEspecialidade(cuidadorDto.especialidade() != null ? cuidadorDto.especialidade().trim() : null);
            cuidador.setTurno(cuidadorDto.turno() != null ? cuidadorDto.turno().trim() : null);

            return cuidadorRepository.save(cuidador);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar cuidador: " + e.getMessage());
        }
    }

    public Cuidador update(Long id, CuidadorDto cuidadorDto) {
        try {
            Cuidador cuidadorExistente = findById(id);

            if (cuidadorDto.nome() == null || cuidadorDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do cuidador é obrigatório");
            }


            if (!cuidadorExistente.getNome().equals(cuidadorDto.nome().trim()) &&
                cuidadorRepository.existsByNomeAndIdNot(cuidadorDto.nome().trim(), id)) {
                throw new RuntimeException("Já existe um cuidador com o nome '" + cuidadorDto.nome().trim() + "'");
            }

            // Atualiza dados
            cuidadorExistente.setNome(cuidadorDto.nome().trim());
            cuidadorExistente.setEspecialidade(cuidadorDto.especialidade() != null ? cuidadorDto.especialidade().trim() : null);
            cuidadorExistente.setTurno(cuidadorDto.turno() != null ? cuidadorDto.turno().trim() : null);

            return cuidadorRepository.save(cuidadorExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar cuidador com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            Cuidador cuidador = findById(id);


            if (cuidador.getAnimais() != null && !cuidador.getAnimais().isEmpty()) {
                throw new RuntimeException(
                        "Não é possível remover o cuidador '" + cuidador.getNome() +
                                "' pois ele está responsável por " + cuidador.getAnimais().size() + " animais. " +
                                "Atribua novos cuidadores aos animais antes de remover."
                );
            }

            cuidadorRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover cuidador com ID " + id + ": " + e.getMessage());
        }
    }
}