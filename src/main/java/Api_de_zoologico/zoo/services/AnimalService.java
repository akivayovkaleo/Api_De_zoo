package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.AnimalDto;
import Api_de_zoologico.zoo.models.*;
import Api_de_zoologico.zoo.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {
    private AnimalRepository animalRepository;
    private CuidadorRepository cuidadorRepository;
    private HabitatRepository habitatRepository;
    private AlimentacaoRepository alimentacaoRepository;
    private EspecieRepository especieRepository;

    public AnimalService(EspecieRepository especieRepository, AnimalRepository animalRepository, AlimentacaoRepository alimentacaoRepository, CuidadorRepository cuidadorRepository, HabitatRepository habitatRepository){
        this.animalRepository = animalRepository;
        this.habitatRepository = habitatRepository;
        this.cuidadorRepository = cuidadorRepository;
        this.alimentacaoRepository = alimentacaoRepository;
        this.especieRepository = especieRepository;
    }

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Animal findById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrado"));
    }

    public List<Animal> findByIdade(int idadeMin, int indadeMax) {
        return animalRepository.findByIdadeBetween(idadeMin, indadeMax);
    }

    public List<Animal> findByNome(String nome) {
        return animalRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Animal> findByEspecie(String nome) {
        return animalRepository.findByEspecie_NomeIgnoreCase(nome);
    }


    public Animal create(AnimalDto animalDto) {
        Animal animal = new Animal();

        Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id()).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        Habitat habitat = habitatRepository.findById(animalDto.habitat_id()).orElseThrow(() -> new RuntimeException("Habitat não encontrado"));
        Especie especie = especieRepository.findById(animalDto.especie_id()).orElseThrow(() -> new RuntimeException("Espécie não encontrada"));

        Alimentacao alimentacao = alimentacaoRepository.findById(animalDto.alimentacao_id()).orElseThrow(() -> new RuntimeException("Alimentação não encontrada"));
        ArrayList<Alimentacao> alimentacoes = new ArrayList<>();
        alimentacoes.add(alimentacao);

        int quantidadeAtual = habitat.getAnimais().size();
        if (quantidadeAtual >= habitat.getCapacidadeAnimal()) {
            throw new RuntimeException("Capacidade do habitat atingida.");
        }

        animal.setNome(animalDto.nome());
        animal.setIdade(animalDto.idade());
        animal.setCuidador(cuidador);
        animal.setHabitat(habitat);
        animal.setEspecie(especie);
        animal.setAlimentacoes(alimentacoes);

        return animalRepository.save(animal);
    }

    public Animal update(Long id, AnimalDto animalDto) {
        Animal animal = findById(id);

        Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id()).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        Habitat habitat = habitatRepository.findById(animalDto.habitat_id()).orElseThrow(() -> new RuntimeException("Habitat não encontrado"));
        Especie especie = especieRepository.findById(animalDto.especie_id()).orElseThrow(() -> new RuntimeException("Espécie não encontrada"));

        Alimentacao alimentacao = alimentacaoRepository.findById(animalDto.alimentacao_id()).orElseThrow(() -> new RuntimeException("Alimentação não encontrada"));
        ArrayList<Alimentacao> alimentacoes = new ArrayList<>();
        alimentacoes.add(alimentacao);

        int quantidadeAtual = habitat.getAnimais().size();
        if (quantidadeAtual >= habitat.getCapacidadeAnimal()) {
            throw new RuntimeException("Capacidade do habitat atingida.");
        }

        animal.setNome(animalDto.nome());
        animal.setIdade(animalDto.idade());
        animal.setCuidador(cuidador);
        animal.setHabitat(habitat);
        animal.setEspecie(especie);
        animal.setAlimentacoes(alimentacoes);

        return animalRepository.save(animal);
    }

    public void delete(Long id) {
        Animal animal = findById(id);
        animalRepository.delete(animal);
    }
}
