package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.EspecieDto;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.services.EspecieService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/especies")
public class EspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @Operation(summary = "Cria uma nova espécie")
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody EspecieDto dto,
            HttpServletRequest request) {
        Especie especie = especieService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespostaUtil.success(especie, "Espécie criada com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Lista todas as espécies ou filtra por nome, família ou classe")
    @GetMapping
    public ResponseEntity<?> listar(
            @Parameter(description = "Filtra por nome") @RequestParam(required = false) String nome,
            @Parameter(description = "Filtra por família") @RequestParam(required = false) String familia,
            @Parameter(description = "Filtra por classe") @RequestParam(required = false) String classe,
            HttpServletRequest request
    ) {
        List<Especie> especies = especieService.listarTodos();
        String msg = "Lista de espécies retornada com sucesso";

        if (nome != null && !nome.isEmpty()) {
            especies = especieService.BuscarPorNome(nome);
            msg = "Lista filtrada por nome";
        }

        else if (familia != null && !familia.isEmpty()) {
            especies = especieService.BuscarPorFamilia(familia);
            msg = "Lista filtrada por família";
        }

        else if (classe != null && !classe.isEmpty()) {
            especies = especieService.buscarPorClasse(classe);
            msg = "Lista filtrada por classe";
        }
        if (especies.isEmpty()) {msg = "Lista vazia";}

        return ResponseEntity.ok(
                RespostaUtil.success(especies, msg, request.getRequestURI())
        );
    }

    @Operation(summary = "Busca uma espécie pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "Id da espécie") @PathVariable Long id,
            HttpServletRequest request) {
        Especie especie = especieService.BuscarPorId(id);
        return ResponseEntity.ok(
                RespostaUtil.success(especie, "Espécie encontrada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Atualiza uma espécie existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Id da espécie") @PathVariable Long id,
            @Valid @RequestBody EspecieDto dto,
            HttpServletRequest request) {
        Especie updated = especieService.atualizar(id, dto);
        return ResponseEntity.ok(
                RespostaUtil.success(updated, "Espécie atualizada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Remove uma espécie pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Id da espécie") @PathVariable Long id,
            HttpServletRequest request) {
        especieService.deletar(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Espécie removida com sucesso", request.getRequestURI())
        );
    }
}
