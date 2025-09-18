package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.HabitatDto;
import Api_de_zoologico.zoo.models.Habitat;
import Api_de_zoologico.zoo.repositories.HabitatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitatService {
    private HabitatRepository habitRepo;

    public HabitatService(HabitatRepository habitRepo) {
        this.habitRepo = habitRepo;
    }

    public Habitat save(Habitat habit) {
        return habitRepo.save(habit);
    }

    public List<Habitat> findAll() {
        return habitRepo.findAll();
    }

    public List<Habitat> findByTipo(String tipo) {
        return habitRepo.findByTipo(tipo);
    }

    public Habitat findById(Long id) {
        return habitRepo.findById(id).orElseThrow(()-> new RuntimeException("Não encontrado"));
    }

    public Habitat set(Long id, HabitatDto habitDto) {
        Habitat habitat = findById(id);
        habitat.setNome(habitDto.nome());
        habitat.setTipo(habitDto.tipo());
        habitat.setCapacidadeAnimal(habitDto.capacidadeAnimal());

        return habitRepo.save(habitat);
    }

    public void delete(Long id) {
        habitRepo.deleteById(id);
    }
}
