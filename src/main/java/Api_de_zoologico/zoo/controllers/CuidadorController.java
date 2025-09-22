package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.CuidadorDto;
import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.services.CuidadorService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuidadores")
public class CuidadorController {

    private final CuidadorService cuidadorService;

    public CuidadorController(CuidadorService cuidadorService) {
        this.cuidadorService = cuidadorService;
    }

    @Operation(summary = "Lista todos os cuidadores ou filtra por especialidade e/ou turno")
    @GetMapping
    public ResponseEntity<?> listarTodos(
            @Parameter(description = "Filtra por especialidade") @RequestParam(required = false) String especialidade,
            @Parameter(description = "Filtra por turno") @RequestParam(required = false) String turno,
            HttpServletRequest request
    ) {
        List<Cuidador> cuidadores = cuidadorService.getAll();
        String msg = "Lista de cuidadores retornada com sucesso";

        if (especialidade != null && !especialidade.isEmpty()) {
            cuidadores = cuidadorService.findByEspecialidade(especialidade);
            msg = "Lista de cuidadores filtrada por especialidade";
        }

        else if (turno != null && !turno.isEmpty()) {
            cuidadores = cuidadorService.findByTurno(turno);
            msg = "Lista de cuidadores filtrada por turno";
        }

        if (cuidadores.isEmpty()) {msg = "Lista vazia";}

        return ResponseEntity.ok(
                RespostaUtil.success(cuidadores, msg, request.getRequestURI())
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

    @Operation(summary = "Cria um novo cuidador")
    @PostMapping
    public ResponseEntity<?> criar(
            @Valid @RequestBody CuidadorDto cuidador,
            HttpServletRequest request
    ) {
        Cuidador c = cuidadorService.create(cuidador);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespostaUtil.success(c, "Cuidador criado com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Atualiza um cuidador pelo Id")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "Id do cuidador") @PathVariable Long id,
            @Valid @RequestBody CuidadorDto cuidador,
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
