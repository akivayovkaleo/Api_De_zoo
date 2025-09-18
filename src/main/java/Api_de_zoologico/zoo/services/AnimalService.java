package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.AnimalDto;
import Api_de_zoologico.zoo.models.Animal;
import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.models.Habitat;
import Api_de_zoologico.zoo.repositories.AnimalRepository;
import Api_de_zoologico.zoo.repositories.CuidadorRepository;
import Api_de_zoologico.zoo.repositories.HabitatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    private AnimalRepository animalRepository;
    private CuidadorRepository cuidadorRepository;
    private HabitatRepository habitatRepository;

    public AnimalService(AnimalRepository animalRepository, CuidadorRepository cuidadorRepository, HabitatRepository habitatRepository){
        this.animalRepository = animalRepository;
        this.habitatRepository = habitatRepository;
        this.cuidadorRepository = cuidadorRepository;
    }
    public List<Animal> findAll(){
        return animalRepository.findAll();
    }
    public Animal findById(Long id){
        return animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrado"));
    }

    public List<Animal> findByIdade(int idadeMin, int indadeMax){
        return animalRepository.findByIdadeBetween(idadeMin, indadeMax);
    }

    public List<Animal> findByNome(String nome){
        return animalRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Animal> findByEspecie(String nome){
        return animalRepository.findByEspecie_NomeIgnoreCase(nome);
    }


    public Animal create(AnimalDto animalDto){
        Animal animal = new Animal();

        Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id()).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        Habitat habitat = habitatRepository.findById(animalDto.habitat_id()).orElseThrow(() -> new RuntimeException("Habitat não encontrado"));

        animal.setNome(animalDto.nome());
        animal.setIdade(animalDto.idade());
        animal.setCuidador(cuidador);
        animal.setHabitat(habitat);

        return animalRepository.save(animal);
    }

    public Animal update(Long id, AnimalDto animalDto){
        Animal animal = findById(id);

        Cuidador cuidador = cuidadorRepository.findById(animalDto.cuidador_id()).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        Habitat habitat = habitatRepository.findById(animalDto.habitat_id()).orElseThrow(() -> new RuntimeException("Habitat não encontrado"));

        animal.setNome(animalDto.nome());
        animal.setIdade(animalDto.idade());
        animal.setCuidador(cuidador);
        animal.setHabitat(habitat);

        return animalRepository.save(animal);
    }

    public void delete(Long id){
        Animal animal = findById(id);
        animalRepository.delete(animal);
    }
}
