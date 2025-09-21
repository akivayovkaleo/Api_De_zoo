package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.EspecieDto;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.repositories.EspecieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EspecieService {
    private final EspecieRepository especieRepository;

    public EspecieService(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    public List<Especie> findAll() {
        try {
            return especieRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as espécies: " + e.getMessage());
        }
    }

    public Especie findById(Long id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada com ID: " + id));
    }

    public List<Especie> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome da espécie não pode ser vazio ou nulo");
        }
        try {
            return especieRepository.findByNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar espécies pelo nome '" + nome + "': " + e.getMessage());
        }
    }

    public List<Especie> findByFamilia(String familia) {
        if (familia == null || familia.trim().isEmpty()) {
            throw new RuntimeException("Família não pode ser vazia ou nula");
        }
        try {
            return especieRepository.findByFamiliaContainingIgnoreCase(familia.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar espécies pela família '" + familia + "': " + e.getMessage());
        }
    }

    public List<Especie> findByOrdem(String ordem) {
        if (ordem == null || ordem.trim().isEmpty()) {
            throw new RuntimeException("Ordem não pode ser vazia ou nula");
        }
        try {
            return especieRepository.findByOrdemContainingIgnoreCase(ordem.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar espécies pela ordem '" + ordem + "': " + e.getMessage());
        }
    }

    public List<Especie> findByClasse(String classe) {
        if (classe == null || classe.trim().isEmpty()) {
            throw new RuntimeException("Classe não pode ser vazia ou nula");
        }
        try {
            return especieRepository.findByClasseContainingIgnoreCase(classe.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar espécies pela classe '" + classe + "': " + e.getMessage());
        }
    }

    public Especie create(EspecieDto especieDto) {
        try {
            if (especieDto.nome() == null || especieDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome da espécie é obrigatório");
            }

            if (especieRepository.findByNome(especieDto.nome().trim()).isPresent()) {
                throw new RuntimeException("Já existe uma espécie cadastrada com o nome '" + especieDto.nome().trim() + "'");
            }

            Especie especie = new Especie();
            especie.setNome(especieDto.nome().trim());
            especie.setDescricao(especieDto.descricao() != null ? especieDto.descricao().trim() : null);
            especie.setNomeCientifico(especieDto.nomeCientifico() != null ? especieDto.nomeCientifico().trim() : null);
            especie.setFamilia(especieDto.familia() != null ? especieDto.familia().trim() : null);
            especie.setOrdem(especieDto.ordem() != null ? especieDto.ordem().trim() : null);
            especie.setClasse(especieDto.classe() != null ? especieDto.classe().trim() : null);

            return especieRepository.save(especie);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar espécie: " + e.getMessage());
        }
    }

    public Especie update(Long id, EspecieDto especieDto) {
        try {
            Especie especieExistente = findById(id);

            if (especieDto.nome() == null || especieDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome da espécie é obrigatório");
            }

            if (!especieExistente.getNome().equals(especieDto.nome().trim()) &&
                    especieRepository.existsByNomeAndIdNot(especieDto.nome().trim(), id)) {
                throw new RuntimeException("Já existe uma espécie cadastrada com o nome '" + especieDto.nome().trim() + "'");
            }

            especieExistente.setNome(especieDto.nome().trim());
            especieExistente.setDescricao(especieDto.descricao() != null ? especieDto.descricao().trim() : null);
            especieExistente.setNomeCientifico(especieDto.nomeCientifico() != null ? especieDto.nomeCientifico().trim() : null);
            especieExistente.setFamilia(especieDto.familia() != null ? especieDto.familia().trim() : null);
            especieExistente.setOrdem(especieDto.ordem() != null ? especieDto.ordem().trim() : null);
            especieExistente.setClasse(especieDto.classe() != null ? especieDto.classe().trim() : null);

            return especieRepository.save(especieExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar espécie com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            Especie especie = findById(id);

            if (especie.getAnimais() != null && !especie.getAnimais().isEmpty()) {
                throw new RuntimeException(
                        "Não é possível remover a espécie '" + especie.getNome() +
                                "' pois ela está associada a " + especie.getAnimais().size() + " animais. " +
                                "Remova os animais ou associe-os a outra espécie antes de remover."
                );
            }

            especieRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover espécie com ID " + id + ": " + e.getMessage());
        }
    }
}