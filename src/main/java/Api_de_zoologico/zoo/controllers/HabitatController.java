package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.HabitatDto;
import Api_de_zoologico.zoo.models.Habitat;
import Api_de_zoologico.zoo.services.HabitatService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/habitats")
public class HabitatController {
    private final HabitatService habitServ;

    public HabitatController(HabitatService habitServ) {
        this.habitServ = habitServ;
    }

    @Operation(summary = "Cria um novo habitat")
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody HabitatDto habit,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespostaUtil.success(habitServ.save(habit), "Habitat criado com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Lista habitats (todos ou filtrados por tipo)")
    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String tipo,
            HttpServletRequest request
    ) {
        List<Habitat> habitats = (tipo != null && !tipo.isEmpty())
                ? habitServ.findByTipo(tipo)
                : habitServ.findAll();

        return ResponseEntity.ok(
                RespostaUtil.success(habitats, "Lista de habitats retornada com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Busca um habitat pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Long id,
            HttpServletRequest request) {
        Habitat habitat = habitServ.findById(id);
        return ResponseEntity.ok(
                RespostaUtil.success(habitat, "Habitat encontrado com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Atualiza um habitat existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody HabitatDto habitDto,
            HttpServletRequest request
    ) {
        Habitat habitat = habitServ.set(id, habitDto);
        return ResponseEntity.ok(
                RespostaUtil.success(habitat, "Habitat atualizado com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Remove um habitat pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        habitServ.delete(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Habitat removido com sucesso", request.getRequestURI()));
    }
}
