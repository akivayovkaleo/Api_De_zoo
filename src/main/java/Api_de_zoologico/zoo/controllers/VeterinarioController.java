package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.services.VeterinarioService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os veterinários ou filtra por especialidade"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de veterinários retornada com sucesso"
    )
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String especialidade,
            HttpServletRequest request
    ) {
        List<Veterinario> veterinarios;

        if (especialidade != null && !especialidade.isEmpty()) {
            veterinarios = veterinarioService.findByEspecialidade(especialidade);
            return ResponseEntity.ok(
                    RespostaUtil.success(veterinarios, "Lista de veterinários filtrada por especialidade", request.getRequestURI())
            );
        }

        veterinarios = veterinarioService.findAll();
        return ResponseEntity.ok(
                RespostaUtil.success(veterinarios, "Lista de veterinários retornada com sucesso", request.getRequestURI())
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um veterinário pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Veterinário encontrado com sucesso"
    )
    public ResponseEntity<?> getById(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Veterinario vet = veterinarioService.findById(id);
        return ResponseEntity.ok(
                RespostaUtil.success(vet, "Veterinário encontrado", request.getRequestURI())
        );
    }

    @PostMapping
    @Operation(
            summary = "Cria um veterinário"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Veterinário criado com sucesso"
    )
    public ResponseEntity<?> add(
            @RequestBody @Valid Veterinario veterinario,
            HttpServletRequest request
    ) {
        Veterinario created = veterinarioService.create(veterinario);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RespostaUtil.success(created, "Veterinário criado com sucesso", request.getRequestURI())
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um veterinário a partir do ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Veterinário atualizado com sucesso"
    )
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid Veterinario veterinario,
            HttpServletRequest request) {
        Veterinario updated = veterinarioService.update(id, veterinario);
        return ResponseEntity.ok(
                RespostaUtil.success(updated, "Veterinário atualizado com sucesso", request.getRequestURI())
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove um veterinário pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Veterinário removido com sucesso"
    )
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        veterinarioService.delete(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Veterinário removido com sucesso", request.getRequestURI())
        );
    }
}
