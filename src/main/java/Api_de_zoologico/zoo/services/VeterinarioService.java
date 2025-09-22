package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.VeterinarioDto;
import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.repositories.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VeterinarioService {
    private VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository){
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<Veterinario> findAll(){
        return veterinarioRepository.findAll();
    }

    public Veterinario findById(Long id){
        return veterinarioRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Veterinário não encontrado"));
    }

    public List<Veterinario> findByEspecialidade(String especialidade){
        return veterinarioRepository.findByEspecialidade(especialidade);
    }
    
    public Veterinario create(VeterinarioDto veterinarioDto){
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(veterinarioDto.nome());
        veterinario.setCrmv(veterinarioDto.crmv());
        veterinario.setEspecialidade(veterinarioDto.especialidade());

        return veterinarioRepository.save(veterinario);
    }

    public Veterinario update(Long id, VeterinarioDto veterinarioDto){
        Veterinario veterinario = findById(id);
        veterinario.setNome(veterinarioDto.nome());
        veterinario.setCrmv(veterinarioDto.crmv());
        veterinario.setEspecialidade(veterinarioDto.especialidade());
        return veterinarioRepository.save(veterinario);
    }

    public void delete(Long id){
        Veterinario veterinario = findById(id);
        veterinarioRepository.delete(veterinario);
    }
}
