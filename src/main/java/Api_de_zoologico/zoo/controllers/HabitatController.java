package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.HabitatDto;
import Api_de_zoologico.zoo.models.Habitat;
import Api_de_zoologico.zoo.services.HabitatService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/habitats")
public class HabitatController {
    private HabitatService habitServ;

    public HabitatController(HabitatService habitServ) {
        this.habitServ = habitServ;
    }

    @PostMapping
    public ResponseEntity<?> registerHabitat(@RequestBody Habitat habit) {
        try {
            return ResponseEntity.ok(habitServ.save(habit));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao criar habitat",
                            e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllHabitats() {
        try {
            return ResponseEntity.of(Optional.ofNullable(habitServ.findAll()));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao listar habitats",
                            e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tipo")
    public ResponseEntity<?> findHabitatByTipo(
            @RequestParam String tipo
    ) {
        try {
            return ResponseEntity.ok(habitServ.findByTipo(tipo));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao encontrar habitat do tipo " + tipo,
                            e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findHabitatById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(habitServ.findById(id));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse("Falha ao encontrar habitat",
                    e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterHabitat(@PathVariable Long id, @RequestBody HabitatDto habitDto) {
        try {
            return ResponseEntity.ok(habitServ.set(id, habitDto));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao alterar habitat",
                            e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHabitat(@PathVariable Long id) {
        try {
            habitServ.delete(id);

            return ResponseEntity.ok("Habitat removido");
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao remover habitat",
                            e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
