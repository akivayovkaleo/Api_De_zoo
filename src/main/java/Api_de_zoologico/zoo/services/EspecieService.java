package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.EspecieDto;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.repositories.EspecieRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    public Especie criar(EspecieDto dto) {
        Especie especie = new Especie();
        especie.setNome(dto.nome());
        especie.setDescricao(dto.descricao());
        especie.setNomeCientifico(dto.nomeCientifico());
        especie.setFamilia(dto.familia());
        especie.setOrdem(dto.ordem());
        especie.setClasse(dto.classe());
        return especieRepository.save(especie);
    }

    public List<Especie> listarTodos() {
        return especieRepository.findAll();
    }

    public Especie BuscarPorId(Long id) {
        return especieRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Espécie não encontrada"));
    }

    public Especie atualizar(Long id, EspecieDto dto) {
        Especie especie = BuscarPorId(id);
        especie.setNome(dto.nome());
        especie.setDescricao(dto.descricao());
        especie.setNomeCientifico(dto.nomeCientifico());
        especie.setFamilia(dto.familia());
        especie.setOrdem(dto.ordem());
        especie.setClasse(dto.classe());
        return especieRepository.save(especie);
    }
    public void deletar(Long id) {
      Especie especie = BuscarPorId(id);
      especieRepository.delete(especie);
    }

    public List<Especie> BuscarPorNome(String nome) {
        return especieRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Especie> BuscarPorFamilia(String familia) {
        return especieRepository.findByFamilia(familia);
    }

    public List<Especie> buscarPorClasse(String classe) {
        return especieRepository.findByClasse(classe);
    }
    }