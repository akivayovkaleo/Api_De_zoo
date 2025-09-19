package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.services.VeterinarioService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {
    private VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping()
    public ResponseEntity<List<Veterinario>> getAll(){
        return ResponseEntity.ok(veterinarioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um veterinário pelo Id")
    @ApiResponse(responseCode = "200", description = "Veterinário encontrado com sucesso")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(veterinarioService.findById(id));
        }catch (RuntimeException e){
            return RespostaUtil.buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/especialidade")
    @Operation(summary = "Filtra veterinários por especialidade")
    @ApiResponse(responseCode = "200", description = "Lista de veterinários retornada com sucesso")
    public ResponseEntity<?> getByEspecialidade(
            @RequestParam  String especialidade
    ) {
        try {
            return ResponseEntity.ok(veterinarioService.findByEspecialidade(especialidade));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping
    @Operation(summary = "Cria um veterinário")
    @ApiResponse(responseCode = "200", description = "Veterinário criado com sucesso")
    public ResponseEntity<?> add(@RequestBody Veterinario veterinario) {
        return ResponseEntity.ok(veterinarioService.create(veterinario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um veterinário apartir do Id")
    @ApiResponse(responseCode = "200", description = "Veterinário atualizado com sucesso")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Veterinario veterinario) {
        try{
            return ResponseEntity.ok(veterinarioService.update(id, veterinario));
        }catch (RuntimeException e){
            return RespostaUtil.buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            veterinarioService.delete(id);
            return ResponseEntity.ok("Veterinario removido");
        }catch (RuntimeException e){
            return RespostaUtil.buildErrorResponse("Veterinário não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
