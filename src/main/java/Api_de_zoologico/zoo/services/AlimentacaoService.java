package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.models.Alimentacao;
import Api_de_zoologico.zoo.models.Animal;
import Api_de_zoologico.zoo.repositories.AlimentacaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlimentacaoService {
    private final AlimentacaoRepository alimentacaoRepository;

    public AlimentacaoService(AlimentacaoRepository alimentacaoRepository) {
        this.alimentacaoRepository = alimentacaoRepository;
    }

    public Alimentacao create(Alimentacao alimentacao) {
        return alimentacaoRepository.save(alimentacao);
    }

    public Alimentacao findById(Long id) {
        return alimentacaoRepository.findById(id).orElseThrow(()->new NoSuchElementException("Alimentação não Encontrada"));
    }

    public Alimentacao update(Long id, Alimentacao alimentacaoAtualizado) {
        Alimentacao alimentacao = findById(id);
        alimentacao.setAnimal(alimentacaoAtualizado.getAnimal());
        alimentacao.setQuantidadeDiaria(alimentacaoAtualizado.getQuantidadeDiaria());
        alimentacao.setTipoComida(alimentacaoAtualizado.getTipoComida());
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
