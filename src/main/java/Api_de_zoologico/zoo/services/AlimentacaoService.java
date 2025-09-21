package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.AlimentacaoDto;
import Api_de_zoologico.zoo.models.Alimentacao;
import Api_de_zoologico.zoo.models.Animal;
import Api_de_zoologico.zoo.repositories.AlimentacaoRepository;
import Api_de_zoologico.zoo.repositories.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlimentacaoService {
    private final AlimentacaoRepository alimentacaoRepository;
    private final AnimalRepository animalRepository;

    public AlimentacaoService(AlimentacaoRepository alimentacaoRepository, AnimalRepository animalRepository) {
        this.alimentacaoRepository = alimentacaoRepository;
        this.animalRepository = animalRepository;
    }

    public List<Alimentacao> findAll() {
        try {
            return alimentacaoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as alimentações: " + e.getMessage());
        }
    }

    public Alimentacao findById(Long id) {
        return alimentacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimentação não encontrada com ID: " + id));
    }

    public List<Alimentacao> findByTipoComida(String tipoComida) {
        if (tipoComida == null || tipoComida.trim().isEmpty()) {
            throw new RuntimeException("Tipo de comida não pode ser vazio ou nulo");
        }
        try {
            return alimentacaoRepository.findByTipoComidaContainingIgnoreCase(tipoComida.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar alimentações pelo tipo '" + tipoComida + "': " + e.getMessage());
        }
    }

    public List<Alimentacao> findByAnimalId(Long animalId) {
        if (animalId == null) {
            throw new RuntimeException("ID do animal não pode ser nulo");
        }
        try {
            return alimentacaoRepository.findByAnimalId(animalId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar alimentações do animal com ID " + animalId + ": " + e.getMessage());
        }
    }

    public Alimentacao create(AlimentacaoDto alimentacaoDto) {
        try {
            if (alimentacaoDto.tipoComida() == null || alimentacaoDto.tipoComida().trim().isEmpty()) {
                throw new RuntimeException("Tipo de comida é obrigatório");
            }

            if (alimentacaoDto.quantidadeDiaria() == null || alimentacaoDto.quantidadeDiaria() <= 0) {
                throw new RuntimeException("Quantidade diária deve ser um número positivo");
            }

            if (alimentacaoDto.animal_id() == null) {
                throw new RuntimeException("ID do animal é obrigatório");
            }

            Animal animal = animalRepository.findById(alimentacaoDto.animal_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Animal não encontrado. ID informado: " + alimentacaoDto.animal_id() +
                                    ". Verifique se o ID do animal está correto."
                    ));

            List<Alimentacao> alimentacoesExistentes = alimentacaoRepository.findByAnimalId(alimentacaoDto.animal_id());
            for (Alimentacao a : alimentacoesExistentes) {
                if (a.getTipoComida().equalsIgnoreCase(alimentacaoDto.tipoComida().trim())) {
                    throw new RuntimeException(
                        "Já existe uma alimentação cadastrada para o animal '" + animal.getNome() +
                        "' com o tipo de comida '" + alimentacaoDto.tipoComida().trim() + "'"
                    );
                }
            }

            Alimentacao alimentacao = new Alimentacao();
            alimentacao.setTipoComida(alimentacaoDto.tipoComida().trim());
            alimentacao.setQuantidadeDiaria(alimentacaoDto.quantidadeDiaria());
            alimentacao.setAnimal(animal);

            return alimentacaoRepository.save(alimentacao);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar alimentação: " + e.getMessage());
        }
    }

    public Alimentacao update(Long id, AlimentacaoDto alimentacaoDto) {
        try {
            Alimentacao alimentacaoExistente = findById(id);

            if (alimentacaoDto.tipoComida() == null || alimentacaoDto.tipoComida().trim().isEmpty()) {
                throw new RuntimeException("Tipo de comida é obrigatório");
            }

            if (alimentacaoDto.quantidadeDiaria() == null || alimentacaoDto.quantidadeDiaria() <= 0) {
                throw new RuntimeException("Quantidade diária deve ser um número positivo");
            }

            if (alimentacaoDto.animal_id() == null) {
                throw new RuntimeException("ID do animal é obrigatório");
            }

            Animal animal = animalRepository.findById(alimentacaoDto.animal_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Animal não encontrado. ID informado: " + alimentacaoDto.animal_id() +
                                    ". Verifique se o ID do animal está correto."
                    ));

            if (!alimentacaoExistente.getTipoComida().equals(alimentacaoDto.tipoComida().trim()) &&
                alimentacaoRepository.findByAnimalId(alimentacaoDto.animal_id()).stream()
                    .anyMatch(a -> a.getTipoComida().equalsIgnoreCase(alimentacaoDto.tipoComida().trim()) &&
                                  !a.getId().equals(id))) {
                throw new RuntimeException(
                    "Já existe uma alimentação cadastrada para o animal '" + animal.getNome() +
                    "' com o tipo de comida '" + alimentacaoDto.tipoComida().trim() + "'"
                );
            }

            alimentacaoExistente.setTipoComida(alimentacaoDto.tipoComida().trim());
            alimentacaoExistente.setQuantidadeDiaria(alimentacaoDto.quantidadeDiaria());
            alimentacaoExistente.setAnimal(animal);

            return alimentacaoRepository.save(alimentacaoExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar alimentação com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            alimentacaoRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover alimentação com ID " + id + ": " + e.getMessage());
        }
    }
}