package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.VisitanteDto;
import Api_de_zoologico.zoo.models.Visitante;
import Api_de_zoologico.zoo.repositories.VisitanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class VisitanteService {
    private final VisitanteRepository visitanteRepository;

    public VisitanteService(VisitanteRepository visitanteRepository) {
        this.visitanteRepository = visitanteRepository;
    }

    public List<Visitante> findAll() {
        try {
            return visitanteRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os visitantes: " + e.getMessage());
        }
    }

    public Visitante findById(Long id) {
        return visitanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitante não encontrado com ID: " + id));
    }

    public List<Visitante> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome não pode ser vazio ou nulo");
        }
        try {
            return visitanteRepository.findByNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar visitantes pelo nome '" + nome + "': " + e.getMessage());
        }
    }

    public List<Visitante> findByCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new RuntimeException("CPF não pode ser vazio ou nulo");
        }
        try {
            return visitanteRepository.findByCpfContaining(cpf.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar visitantes pelo CPF '" + cpf + "': " + e.getMessage());
        }
    }

    public List<Visitante> findByFaixaEtaria(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new RuntimeException("Datas de início e fim são obrigatórias");
        }
        if (dataInicio.isAfter(dataFim)) {
            throw new RuntimeException("Data de início não pode ser posterior à data de fim");
        }
        try {
            return visitanteRepository.findByDataNascimentoBetween(dataInicio, dataFim);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar visitantes pela faixa etária: " + e.getMessage());
        }
    }

    public Visitante create(VisitanteDto visitanteDto) {
        try {
            if (visitanteDto.nome() == null || visitanteDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do visitante é obrigatório");
            }

            if (visitanteDto.cpf() == null || visitanteDto.cpf().trim().isEmpty()) {
                throw new RuntimeException("CPF do visitante é obrigatório");
            }

            if (visitanteDto.dataNascimento() == null) {
                throw new RuntimeException("Data de nascimento do visitante é obrigatória");
            }

            String cpfLimpo = visitanteDto.cpf().replaceAll("\\D", "");
            if (cpfLimpo.length() != 11) {
                throw new RuntimeException("CPF deve conter 11 dígitos");
            }

            if (visitanteRepository.findByCpf(cpfLimpo).isPresent()) {
                throw new RuntimeException("Já existe um visitante cadastrado com o CPF '" + cpfLimpo + "'");
            }

            Visitante visitante = new Visitante();
            visitante.setNome(visitanteDto.nome().trim());
            visitante.setCpf(cpfLimpo);
            visitante.setDataNascimento(visitanteDto.dataNascimento());
            visitante.setTelefone(visitanteDto.telefone());
            visitante.setDataCadastro(LocalDate.now());

            return visitanteRepository.save(visitante);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar visitante: " + e.getMessage());
        }
    }

    public Visitante update(Long id, VisitanteDto visitanteDto) {
        try {
            Visitante visitanteExistente = findById(id);

            if (visitanteDto.nome() == null || visitanteDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do visitante é obrigatório");
            }

            if (visitanteDto.cpf() == null || visitanteDto.cpf().trim().isEmpty()) {
                throw new RuntimeException("CPF do visitante é obrigatório");
            }

            if (visitanteDto.dataNascimento() == null) {
                throw new RuntimeException("Data de nascimento do visitante é obrigatória");
            }

            String cpfLimpo = visitanteDto.cpf().replaceAll("\\D", "");
            if (cpfLimpo.length() != 11) {
                throw new RuntimeException("CPF deve conter 11 dígitos");
            }

            if (!visitanteExistente.getCpf().equals(cpfLimpo) &&
                    visitanteRepository.existsByCpfAndIdNot(cpfLimpo, id)) {
                throw new RuntimeException("Já existe um visitante cadastrado com o CPF '" + cpfLimpo + "'");
            }

            visitanteExistente.setNome(visitanteDto.nome().trim());
            visitanteExistente.setCpf(cpfLimpo);
            visitanteExistente.setDataNascimento(visitanteDto.dataNascimento());
            visitanteExistente.setTelefone(visitanteDto.telefone());

            return visitanteRepository.save(visitanteExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar visitante com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            visitanteRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover visitante com ID " + id + ": " + e.getMessage());
        }
    }
}