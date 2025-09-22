package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.EspecieDto;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.services.EspecieService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especies")
public class EspecieController {

    private final EspecieService especieService;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    @PostMapping
    @Operation(
            summary = "Cria uma nova espécie"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Espécie criada com sucesso"
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody EspecieDto dto,
            HttpServletRequest request) {
        Especie especie = especieService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RespostaUtil.success(especie, "Espécie criada com sucesso", request.getRequestURI())
        );
    }

    @GetMapping
    @Operation(
            summary = "Lista todas as espécies ou filtra por nome, família ou classe"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de espécies retornada com sucesso"
    )
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String familia,
            @RequestParam(required = false) String classe,
            HttpServletRequest request
    ) {
        List<Especie> especies;

        if (nome != null && !nome.isEmpty()) {
            especies = especieService.BuscarPorNome(nome);
        } else if (familia != null && !familia.isEmpty()) {
            especies = especieService.BuscarPorFamilia(familia);
        } else if (classe != null && !classe.isEmpty()) {
            especies = especieService.buscarPorClasse(classe);
        } else {
            especies = especieService.listarTodos();
        }

        return ResponseEntity.ok(
                RespostaUtil.success(especies, "Lista de espécies retornada com sucesso", request.getRequestURI())
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca uma espécie pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Espécie encontrada com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = "Espécie não encontrada"
    )
    public ResponseEntity<?> findById(
            @PathVariable Long id,
            HttpServletRequest request) {
        Especie especie = especieService.BuscarPorId(id);
        return ResponseEntity.ok(
                RespostaUtil.success(especie, "Espécie encontrada", request.getRequestURI())
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza uma espécie existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Espécie atualizada com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Espécie não encontrada"
    )
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody EspecieDto dto,
            HttpServletRequest request) {
        Especie updated = especieService.atualizar(id, dto);
        return ResponseEntity.ok(
                RespostaUtil.success(updated, "Espécie atualizada com sucesso", request.getRequestURI())
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove uma espécie pelo ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Espécie removida com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Espécie não encontrada"
    )
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            HttpServletRequest request) {
        especieService.deletar(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Espécie removida com sucesso", request.getRequestURI())
        );
    }
}
