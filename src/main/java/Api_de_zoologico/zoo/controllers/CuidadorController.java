package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.services.CuidadorService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuidadores")
public class CuidadorController {

    private final CuidadorService cuidadorService;

    public CuidadorController(CuidadorService cuidadorService) {
        this.cuidadorService = cuidadorService;
    }

    @Operation(summary = "Cria um novo cuidador")
    @PostMapping
    public ResponseEntity<?> criar(
            @Valid @RequestBody Cuidador cuidador,
            HttpServletRequest request
    ) {
        Cuidador c = cuidadorService.create(cuidador);
        return ResponseEntity.ok(
                RespostaUtil.success(c, "Cuidador criado com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Lista todos os cuidadores")
    @GetMapping
    public ResponseEntity<?> listarTodos(HttpServletRequest request) {
        List<Cuidador> cuidadores = cuidadorService.getAll();
        return ResponseEntity.ok(
                RespostaUtil.success(cuidadores, "Lista de cuidadores retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Busca um cuidador pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "Id do cuidador") @PathVariable Long id,
            HttpServletRequest request
    ) {
        Cuidador c = cuidadorService.findById(id);
        return ResponseEntity.ok(
                RespostaUtil.success(c, "Cuidador retornado com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Filtra cuidadores por especialidade")
    @GetMapping("/especialidade")
    public ResponseEntity<?> getByEspecialidade(
            @Parameter(description = "Especialidade do cuidador") @RequestParam String especialidade,
            HttpServletRequest request
    ) {
        List<Cuidador> cuidadores = cuidadorService.findByEspecialidade(especialidade);
        return ResponseEntity.ok(
                RespostaUtil.success(cuidadores, "Lista de cuidadores retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Filtra cuidadores por turno")
    @GetMapping("/turno")
    public ResponseEntity<?> getByTurno(
            @Parameter(description = "Turno do cuidador") @RequestParam String turno,
            HttpServletRequest request
    ) {
        List<Cuidador> cuidadores = cuidadorService.findByTurno(turno);
        return ResponseEntity.ok(
                RespostaUtil.success(cuidadores, "Lista de cuidadores retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Atualiza um cuidador pelo Id")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "Id do cuidador") @PathVariable Long id,
            @Valid @RequestBody Cuidador cuidador,
            HttpServletRequest request
    ) {
        Cuidador c = cuidadorService.update(id, cuidador);
        return ResponseEntity.ok(
                RespostaUtil.success(c, "Cuidador atualizado com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Deleta um cuidador pelo Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "Id do cuidador") @PathVariable Long id,
            HttpServletRequest request
    ) {
        cuidadorService.delete(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Cuidador deletado com sucesso", request.getRequestURI())
        );
    }
}
