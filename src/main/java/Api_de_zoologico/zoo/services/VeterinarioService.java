package Api_de_zoologico.zoo.services;

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
    
    public Veterinario create(Veterinario veterinario){
        return veterinarioRepository.save(veterinario);
    }

    public Veterinario update(Long id, Veterinario veterinarioAtualizado){
        Veterinario veterinario = findById(id);
        veterinario.setNome(veterinarioAtualizado.getNome());
        veterinario.setCrmv(veterinarioAtualizado.getCrmv());
        veterinario.setEspecialidade(veterinarioAtualizado.getEspecialidade());
        return veterinarioRepository.save(veterinario);
    }

    public void delete(Long id){
        Veterinario veterinario = findById(id);
        veterinarioRepository.delete(veterinario);
    }
}
