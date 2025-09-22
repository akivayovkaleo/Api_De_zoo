package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.AnimalDto;
import Api_de_zoologico.zoo.models.*;
import Api_de_zoologico.zoo.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AnimalService {
    private AnimalRepository animalRepository;
    private CuidadorRepository cuidadorRepository;
    private HabitatRepository habitatRepository;
    private AlimentacaoRepository alimentacaoRepository;
    private EspecieRepository especieRepository;
    private EmailService emailService;

    public AnimalService(EmailService emailService, EspecieRepository especieRepository, AnimalRepository animalRepository, AlimentacaoRepository alimentacaoRepository, CuidadorRepository cuidadorRepository, HabitatRepository habitatRepository){
        this.animalRepository = animalRepository;
        this.habitatRepository = habitatRepository;
        this.cuidadorRepository = cuidadorRepository;
        this.alimentacaoRepository = alimentacaoRepository;
        this.especieRepository = especieRepository;
        this.emailService = emailService;
    }

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Animal findById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Animal não encontrado"));
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

        Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id()).orElseThrow(() -> new NoSuchElementException("Cuidador não encontrado"));
        Habitat habitat = habitatRepository.findById(animalDto.habitat_id()).orElseThrow(() -> new NoSuchElementException("Habitat não encontrado"));
        Especie especie = especieRepository.findById(animalDto.especie_id()).orElseThrow(() -> new NoSuchElementException("Espécie não encontrada"));
        Alimentacao alimentacao = alimentacaoRepository.findById(animalDto.alimentacao_id()).orElseThrow(() -> new NoSuchElementException("Alimentação não encontrada"));

        if (habitat.getAnimais().size() >= habitat.getCapacidadeAnimal()) {
            throw new IllegalStateException("Capacidade do habitat atingida.");
        }

        animal.setNome(animalDto.nome());
        animal.setIdade(animalDto.idade());
        animal.setCuidador(cuidador);
        animal.setHabitat(habitat);
        animal.setEspecie(especie);

        alimentacao.setAnimal(animal);
        List<Alimentacao> alimentacoes = new ArrayList<>();
        alimentacoes.add(alimentacao);
        animal.setAlimentacoes(alimentacoes);


        return animalRepository.save(animal);
    }

    public Animal update(Long id, AnimalDto animalDto) {
        Animal animal = findById(id);

        Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id()).orElseThrow(() -> new NoSuchElementException("Cuidador não encontrado"));
        Habitat habitat = habitatRepository.findById(animalDto.habitat_id()).orElseThrow(() -> new NoSuchElementException("Habitat não encontrado"));
        Especie especie = especieRepository.findById(animalDto.especie_id()).orElseThrow(() -> new NoSuchElementException("Espécie não encontrada"));
        Alimentacao alimentacao = alimentacaoRepository.findById(animalDto.alimentacao_id()).orElseThrow(() -> new NoSuchElementException("Alimentação não encontrada"));

        if (habitat.getAnimais().size() >= habitat.getCapacidadeAnimal()) {
            throw new IllegalStateException("Capacidade do habitat atingida.");
        }

        if (!animal.getAlimentacoes().contains(alimentacao)) {
            animal.getAlimentacoes().add(alimentacao);
        }

        animal.setNome(animalDto.nome());
        animal.setIdade(animalDto.idade());
        animal.setCuidador(cuidador);
        animal.setHabitat(habitat);
        animal.setEspecie(especie);

        animal = animalRepository.save(animal);

        emailService.sendEmail(animal.getCuidador().getEmail(),
                "Animal atualizado",
                "O animal " + animal.getNome() + " foi atualizado.\n" + animal.toString());

        return animal;
    }

    public void delete(Long id) {
        Animal animal = findById(id);
        animalRepository.delete(animal);
    }

}
