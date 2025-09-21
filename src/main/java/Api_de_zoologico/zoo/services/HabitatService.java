package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.HabitatDto;
import Api_de_zoologico.zoo.models.Habitat;
import Api_de_zoologico.zoo.repositories.HabitatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HabitatService {
    private final HabitatRepository habitatRepository;

    public HabitatService(HabitatRepository habitatRepository) {
        this.habitatRepository = habitatRepository;
    }

    public List<Habitat> findAll() {
        try {
            return habitatRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os habitats: " + e.getMessage());
        }
    }

    public Habitat findById(Long id) {
        return habitatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitat não encontrado com ID: " + id));
    }

    public List<Habitat> findByTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new RuntimeException("Tipo de habitat não pode ser vazio ou nulo");
        }
        try {
            return habitatRepository.findByTipoContainingIgnoreCase(tipo.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar habitats pelo tipo '" + tipo + "': " + e.getMessage());
        }
    }

    public List<Habitat> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome do habitat não pode ser vazio ou nulo");
        }
        try {
            return habitatRepository.findByNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar habitats pelo nome '" + nome + "': " + e.getMessage());
        }
    }

    public Habitat create(HabitatDto habitatDto) {
        try {
            if (habitatDto.nome() == null || habitatDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do habitat é obrigatório");
            }

            if (habitatDto.tipo() == null || habitatDto.tipo().trim().isEmpty()) {
                throw new RuntimeException("Tipo do habitat é obrigatório");
            }

            if (habitatDto.capacidadeAnimal() == null || habitatDto.capacidadeAnimal() <= 0) {
                throw new RuntimeException("Capacidade de animais deve ser um número positivo");
            }


            List<Habitat> habitatsExistentes = habitatRepository.findByNomeContainingIgnoreCase(habitatDto.nome().trim());
            if (!habitatsExistentes.isEmpty()) {
                throw new RuntimeException("Já existe um habitat com o nome '" + habitatDto.nome().trim() + "'");
            }


            Habitat habitat = new Habitat();
            habitat.setNome(habitatDto.nome().trim());
            habitat.setTipo(habitatDto.tipo().trim());
            habitat.setCapacidadeAnimal(habitatDto.capacidadeAnimal());

            return habitatRepository.save(habitat);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar habitat: " + e.getMessage());
        }
    }

    public Habitat update(Long id, HabitatDto habitatDto) {
        try {
            Habitat habitatExistente = findById(id);

            if (habitatDto.nome() == null || habitatDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do habitat é obrigatório");
            }

            if (habitatDto.tipo() == null || habitatDto.tipo().trim().isEmpty()) {
                throw new RuntimeException("Tipo do habitat é obrigatório");
            }

            if (habitatDto.capacidadeAnimal() == null || habitatDto.capacidadeAnimal() <= 0) {
                throw new RuntimeException("Capacidade de animais deve ser um número positivo");
            }

            if (habitatExistente.getAnimais() != null &&
                    habitatDto.capacidadeAnimal() < habitatExistente.getAnimais().size()) {
                throw new RuntimeException(
                        "Não é possível reduzir a capacidade para " + habitatDto.capacidadeAnimal() +
                                " pois o habitat já possui " + habitatExistente.getAnimais().size() + " animais"
                );
            }

            habitatExistente.setNome(habitatDto.nome().trim());
            habitatExistente.setTipo(habitatDto.tipo().trim());
            habitatExistente.setCapacidadeAnimal(habitatDto.capacidadeAnimal());

            return habitatRepository.save(habitatExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar habitat com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            Habitat habitat = findById(id);

            if (habitat.getAnimais() != null && !habitat.getAnimais().isEmpty()) {
                throw new RuntimeException(
                        "Não é possível remover o habitat '" + habitat.getNome() +
                                "' pois ele possui " + habitat.getAnimais().size() + " animais associados. " +
                                "Transfira os animais para outro habitat antes de remover."
                );
            }

            habitatRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover habitat com ID " + id + ": " + e.getMessage());
        }
    }
}