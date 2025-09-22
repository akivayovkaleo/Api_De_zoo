package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.VeterinarioDto;
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

    @Operation(summary = "Lista todos os veterinários ou filtra por especialidade")
    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String especialidade,
            HttpServletRequest request
    ) {
        List<Veterinario> veterinarios;
        String msg;

        if (especialidade != null && !especialidade.isEmpty()){
            veterinarios = veterinarioService.findByEspecialidade(especialidade);
            msg = "Lista de veterinários filtrada por especialidade";
        }
        else {
            veterinarios = veterinarioService.findAll();
            msg = "Lista de veterinários retornada com sucesso";
        }

        if (veterinarios.isEmpty()) {msg = "Lista vazia";}

        return ResponseEntity.ok(
                RespostaUtil.success(veterinarios, msg, request.getRequestURI()));
    }

    @Operation(summary = "Busca um veterinário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Veterinario vet = veterinarioService.findById(id);
        return ResponseEntity.ok(
                RespostaUtil.success(vet, "Veterinário encontrado", request.getRequestURI()));
    }

    @Operation(summary = "Cria um veterinário")
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody @Valid VeterinarioDto veterinario,
            HttpServletRequest request
    ) {
        Veterinario created = veterinarioService.create(veterinario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespostaUtil.success(created, "Veterinário criado com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Atualiza um veterinário pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid VeterinarioDto veterinario,
            HttpServletRequest request
    ) {
        Veterinario updated = veterinarioService.update(id, veterinario);
        return ResponseEntity.ok(
                RespostaUtil.success(updated, "Veterinário atualizado com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Remove um veterinário pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        veterinarioService.delete(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Veterinário removido com sucesso", request.getRequestURI()));
    }
}
