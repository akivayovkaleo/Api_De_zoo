package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.CuidadorDto;
import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.repositories.CuidadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service

public class CuidadorService {
    private final CuidadorRepository cuidadorRepository;

    public CuidadorService(CuidadorRepository cuidadorRepository) {
        this.cuidadorRepository = cuidadorRepository;
    }

    public Cuidador create(CuidadorDto cuidadorDto) {
        Cuidador cuidador = new Cuidador();
        cuidador.setNome(cuidadorDto.nome());
        cuidador.setTurno(cuidadorDto.turno());
        cuidador.setEspecialidade(cuidadorDto.especialidade());
        cuidador.setEmail(cuidadorDto.email());

        return cuidadorRepository.save(cuidador);
    }

    public List<Cuidador> getAll() {
        return cuidadorRepository.findAll();
    }

    public Cuidador findById(Long id) {
        return cuidadorRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Cuidador não encontrado"));
    }

    public List<Cuidador> findByEspecialidade(String especialidade) {
        return cuidadorRepository.findByEspecialidade(especialidade);
    }

    public List<Cuidador> findByTurno(String turno) {
        return cuidadorRepository.findByTurno(turno);
    }

    public Cuidador update(Long id,  CuidadorDto cuidadorDto) {
        Cuidador cuidador = findById(id);
        cuidador.setNome(cuidadorDto.nome());
        cuidador.setTurno(cuidadorDto.turno());
        cuidador.setEspecialidade(cuidadorDto.especialidade());
        cuidador.setEmail(cuidadorDto.email());

        return cuidadorRepository.save(cuidador);
    }

    public void delete(Long id) {
        Cuidador cuidador = findById(id);
        cuidadorRepository.delete(cuidador);
    }

}
