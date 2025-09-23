package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.AlimentacaoDto;
import Api_de_zoologico.zoo.models.Alimentacao;
import Api_de_zoologico.zoo.models.Animal;
import Api_de_zoologico.zoo.repositories.AlimentacaoRepository;
import Api_de_zoologico.zoo.repositories.AnimalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlimentacaoService {
    private final AlimentacaoRepository alimentacaoRepository;
    private final AnimalService animalService;

    public AlimentacaoService(AlimentacaoRepository alimentacaoRepository, AnimalService animalService) {
        this.alimentacaoRepository = alimentacaoRepository;
        this.animalService = animalService;
    }

    public Alimentacao create(AlimentacaoDto alimentacaodto) {
        Alimentacao alimentacao = new Alimentacao();
        if (alimentacaodto.animal_id() != null){
            Animal animal = animalService.findById(alimentacaodto.animal_id());
            alimentacao.setAnimal(animal);
        }
        alimentacao.setQuantidadeDiaria(alimentacaodto.quantidadeDiaria());
        alimentacao.setTipoComida(alimentacaodto.tipoComida());

        return alimentacaoRepository.save(alimentacao);
    }

    public Alimentacao findById(Long id) {
        return alimentacaoRepository.findById(id).orElseThrow(()->new NoSuchElementException("Alimentação não Encontrada"));
    }

    public Alimentacao update(Long id, AlimentacaoDto alimentacaodto) {
        Alimentacao alimentacao = findById(id);
        if (alimentacaodto.animal_id() != null){
            Animal animal = animalService.findById(alimentacaodto.animal_id());
            alimentacao.setAnimal(animal);
        }
        alimentacao.setQuantidadeDiaria(alimentacaodto.quantidadeDiaria());
        alimentacao.setTipoComida(alimentacaodto.tipoComida());

        return alimentacaoRepository.save(alimentacao);
    }

    public List<Alimentacao> getAll() {
        return alimentacaoRepository.findAll();
    }

    public List<Alimentacao> buscarPorTipoComida(String tipoComida) {
        return alimentacaoRepository.findByTipoComida(tipoComida);
    }

    public List<Alimentacao> buscarPorAnimalId(Long animal_id) {
        return alimentacaoRepository.findByAnimalId(animal_id);
    }

    public void deletar(Long id) {
        alimentacaoRepository.deleteById(id);
    }
}
