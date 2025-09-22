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

    @PostMapping
    @Operation(
            summary = "Cria um novo habitat"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Habitat criado com sucesso"
    )
    public ResponseEntity<?> registerHabitat(
            @Valid @RequestBody Habitat habit,
            HttpServletRequest request
    ) {
        Habitat habitat = habitServ.save(habit);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RespostaUtil.success(habitat, "Habitat criado com sucesso", request.getRequestURI())
        );
    }

    @GetMapping
    @Operation(
            summary = "Lista habitats (todos ou filtrados por tipo)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de habitats recuperada com sucesso"
    )
    public ResponseEntity<?> findAllHabitats(
            @RequestParam(required = false) String tipo,
            HttpServletRequest request
    ) {
        List<Habitat> habitats = (tipo != null && !tipo.isEmpty())
                ? habitServ.findByTipo(tipo)
                : habitServ.findAll();

        return ResponseEntity.ok(
                RespostaUtil.success(habitats, "Lista de habitats recuperada", request.getRequestURI())
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um habitat pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habitat encontrado com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habitat não encontrado"
    )
    public ResponseEntity<?> findHabitatById(
            @PathVariable Long id,
            HttpServletRequest request) {
        Habitat habitat = habitServ.findById(id);
        return ResponseEntity.ok(
                RespostaUtil.success(habitat, "Habitat encontrado", request.getRequestURI())
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um habitat existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habitat atualizado com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habitat não encontrado"
    )
    public ResponseEntity<?> alterHabitat(
            @PathVariable Long id,
            @Valid @RequestBody HabitatDto habitDto,
            HttpServletRequest request
    ) {
        Habitat habitat = habitServ.set(id, habitDto);
        return ResponseEntity.ok(
                RespostaUtil.success(habitat, "Habitat atualizado com sucesso", request.getRequestURI())
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove um habitat pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habitat removido com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habitat não encontrado"
    )
    public ResponseEntity<?> deleteHabitat(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        habitServ.delete(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Habitat removido com sucesso", request.getRequestURI())
        );
    }
}
