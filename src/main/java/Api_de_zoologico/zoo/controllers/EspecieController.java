package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.EspecieDto;
import Api_de_zoologico.zoo.models.Especie;
import Api_de_zoologico.zoo.repositories.AnimalRepository;
import Api_de_zoologico.zoo.services.EspecieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especies")
public class EspecieController {

    @Autowired
    private EspecieService especieService;

    @PostMapping
    @Operation(summary = "Cria uma nova especie")
    @ApiResponse(responseCode = "200", description = "Especie criada com sucesso")
    public ResponseEntity<Especie> criar(@Valid @RequestBody EspecieDto dto) {
        return ResponseEntity.ok(especieService.criar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todas as especies")
    @ApiResponse(responseCode = "200", description = "Lista de especies com sucesso")
    public ResponseEntity<List<Especie>> listar() {
        return ResponseEntity.ok(especieService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca por id ")
    @ApiResponse(responseCode = "200", description = "Busca por id realizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Especie não foi encontrada")
    public ResponseEntity<Especie> findById(@PathVariable Long id) {
        return ResponseEntity.ok(especieService.BuscarPorId(id));
    }



}
