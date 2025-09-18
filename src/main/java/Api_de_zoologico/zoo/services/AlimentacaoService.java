package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.models.Alimentacao;
import Api_de_zoologico.zoo.repositories.AlimentacaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlimentacaoService {
    private final AlimentacaoRepository alimentacaoRepository;

    public AlimentacaoService(AlimentacaoRepository alimentacaoRepository) {
        this.alimentacaoRepository = alimentacaoRepository;
    }

    public Alimentacao create(Alimentacao alimentacao) {
        return alimentacaoRepository.save(alimentacao);
    }

    public List<Alimentacao> getAll() {
        return alimentacaoRepository.findAll();
    }

    public List<Alimentacao> buscarPorTipoComida(String tipoComida) {
        return alimentacaoRepository.findByTipoComida(tipoComida);
    }

    public List<Alimentacao> buscarPorAnimalId(Long animalId) {
        return alimentacaoRepository.findByAnimalId(animalId);
    }

    public void deletar(Long id) {
        alimentacaoRepository.deleteById(id);
    }
}
