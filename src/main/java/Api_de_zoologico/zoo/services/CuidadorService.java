package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.repositories.CuidadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CuidadorService {
    private final CuidadorRepository cuidadorRepository;

    public CuidadorService(CuidadorRepository cuidadorRepository) {
        this.cuidadorRepository = cuidadorRepository;
    }

    public Cuidador create(Cuidador cuidador) {
        return cuidadorRepository.save(cuidador);
    }

    public List<Cuidador> getAll() {
        return cuidadorRepository.findAll();
    }

    public List<Cuidador> findByEspecialidade(String especialidade) {
        return cuidadorRepository.findByEspecialidade(especialidade);
    }

    public List<Cuidador> findByTurno(String turno) {
        return cuidadorRepository.findByTurno(turno);
    }

    public Cuidador update(Long id,  Cuidador cuidadorAtualizado) {
        Cuidador cuidador = cuidadorRepository.findById(id).orElseThrow(()-> new RuntimeException("Não encontrado"));
        cuidador.setNome(cuidadorAtualizado.getNome());
        cuidador.setTurno(cuidadorAtualizado.getTurno());
        cuidador.setEspecialidade(cuidadorAtualizado.getEspecialidade());
        return cuidadorRepository.save(cuidador);
    }

    public void delete(Long id) {
        cuidadorRepository.deleteById(id);
    }

}
