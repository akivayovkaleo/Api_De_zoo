package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.AnimalDto;
import Api_de_zoologico.zoo.models.*;
import Api_de_zoologico.zoo.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final CuidadorRepository cuidadorRepository;
    private final HabitatRepository habitatRepository;
    private final AlimentacaoRepository alimentacaoRepository;
    private final EspecieRepository especieRepository;

    public AnimalService(
            EspecieRepository especieRepository,
            AnimalRepository animalRepository,
            AlimentacaoRepository alimentacaoRepository,
            CuidadorRepository cuidadorRepository,
            HabitatRepository habitatRepository
    ) {
        this.animalRepository = animalRepository;
        this.habitatRepository = habitatRepository;
        this.cuidadorRepository = cuidadorRepository;
        this.alimentacaoRepository = alimentacaoRepository;
        this.especieRepository = especieRepository;
    }

    public List<Animal> findAll() {
        try {
            return animalRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os animais: " + e.getMessage());
        }
    }

    public Animal findById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado com ID: " + id));
    }

    public List<Animal> findByIdadeBetween(int idadeMin, int idadeMax) {
        if (idadeMin < 0 || idadeMax < 0 || idadeMin > idadeMax) {
            throw new RuntimeException("Intervalo de idade inválido. Idade mínima: " + idadeMin + ", Idade máxima: " + idadeMax);
        }
        try {
            return animalRepository.findByIdadeBetween(idadeMin, idadeMax);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar animais por faixa etária: " + e.getMessage());
        }
    }

    public List<Animal> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome não pode ser vazio ou nulo");
        }
        try {
            return animalRepository.findByNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar animais pelo nome '" + nome + "': " + e.getMessage());
        }
    }

    public List<Animal> findByEspecie(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome da espécie não pode ser vazio ou nulo");
        }
        try {
            return animalRepository.findByEspecieNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar animais pela espécie '" + nome + "': " + e.getMessage());
        }
    }

    public Animal create(AnimalDto animalDto) {
        try {
            if (animalDto.nome() == null || animalDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do animal é obrigatório");
            }

            if (animalDto.idade() < 0) {
                throw new RuntimeException("Idade do animal deve ser um número positivo");
            }

            // Busca entidades relacionadas com mensagens claras
            Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Cuidador não encontrado. ID informado: " + animalDto.cuidador_id() +
                                    ". Verifique se o ID do cuidador está correto."
                    ));

            Habitat habitat = habitatRepository.findById(animalDto.habitat_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Habitat não encontrado. ID informado: " + animalDto.habitat_id() +
                                    ". Verifique se o ID do habitat está correto."
                    ));

            Especie especie = especieRepository.findById(animalDto.especie_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Espécie não encontrada. ID informado: " + animalDto.especie_id() +
                                    ". Verifique se o ID da espécie está correto."
                    ));

            Alimentacao alimentacao = alimentacaoRepository.findById(animalDto.alimentacao_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Alimentação não encontrada. ID informado: " + animalDto.alimentacao_id() +
                                    ". Verifique se o ID da alimentação está correto."
                    ));

            verificarRegrasNegocio(habitat, null);

            Animal animal = new Animal();
            animal.setNome(animalDto.nome().trim());
            animal.setIdade(animalDto.idade());
            animal.setCuidador(cuidador);
            animal.setHabitat(habitat);
            animal.setEspecie(especie);

            Animal animalSalvo = animalRepository.save(animal);

            alimentacao.setAnimal(animalSalvo);
            alimentacaoRepository.save(alimentacao);

            List<Alimentacao> alimentacoes = new ArrayList<>();
            alimentacoes.add(alimentacao);
            animalSalvo.setAlimentacoes(alimentacoes);

            return animalSalvo;

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar animal: " + e.getMessage());
        }
    }

    public Animal update(Long id, AnimalDto animalDto) {
        try {
            Animal animalExistente = findById(id);

            if (animalDto.nome() == null || animalDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do animal é obrigatório");
            }

            if (animalDto.idade() < 0) {
                throw new RuntimeException("Idade do animal deve ser um número positivo");
            }

            Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Cuidador não encontrado. ID informado: " + animalDto.cuidador_id() +
                                    ". Verifique se o ID do cuidador está correto."
                    ));

            Habitat habitat = habitatRepository.findById(animalDto.habitat_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Habitat não encontrado. ID informado: " + animalDto.habitat_id() +
                                    ". Verifique se o ID do habitat está correto."
                    ));

            Especie especie = especieRepository.findById(animalDto.especie_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Espécie não encontrada. ID informado: " + animalDto.especie_id() +
                                    ". Verifique se o ID da espécie está correto."
                    ));

            Alimentacao alimentacao = alimentacaoRepository.findById(animalDto.alimentacao_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Alimentação não encontrada. ID informado: " + animalDto.alimentacao_id() +
                                    ". Verifique se o ID da alimentação está correto."
                    ));

            verificarRegrasNegocio(habitat, id);

            animalExistente.setNome(animalDto.nome().trim());
            animalExistente.setIdade(animalDto.idade());
            animalExistente.setCuidador(cuidador);
            animalExistente.setHabitat(habitat);
            animalExistente.setEspecie(especie);

            if (!animalExistente.getAlimentacoes().isEmpty()) {
                Alimentacao alimentacaoExistente = animalExistente.getAlimentacoes().get(0);
                alimentacaoExistente.setTipoComida(alimentacao.getTipoComida());
                alimentacaoExistente.setQuantidadeDiaria(alimentacao.getQuantidadeDiaria());
                alimentacaoRepository.save(alimentacaoExistente);
            } else {
                alimentacao.setAnimal(animalExistente);
                alimentacaoRepository.save(alimentacao);
                List<Alimentacao> alimentacoes = new ArrayList<>();
                alimentacoes.add(alimentacao);
                animalExistente.setAlimentacoes(alimentacoes);
            }

            return animalRepository.save(animalExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar animal com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            Animal animal = findById(id);
            animalRepository.delete(animal);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao remover animal com ID " + id + ": " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover animal com ID " + id + ": " + e.getMessage());
        }
    }

    // Métodos auxiliares
    private void verificarRegrasNegocio(Habitat habitat, Long animalIdExistente) {
        // Regra: Verifica capacidade do habitat
        long animaisNoHabitat = animalRepository.countByHabitatId(habitat.getId());

        // Se for update, não conta o próprio animal
        if (animalIdExistente != null) {
            animaisNoHabitat--;
        }

        if (animaisNoHabitat >= habitat.getCapacidadeAnimal()) {
            throw new RuntimeException(
                    "Capacidade máxima do habitat '" + habitat.getNome() + "' atingida. " +
                            "Capacidade: " + habitat.getCapacidadeAnimal() + " animais. " +
                            "Animais atuais: " + animaisNoHabitat + ". " +
                            "Não é possível adicionar mais animais a este habitat."
            );
        }
    }
}