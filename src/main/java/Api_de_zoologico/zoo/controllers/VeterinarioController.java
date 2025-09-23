package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.VeterinarioDto;
import Api_de_zoologico.zoo.models.Veterinario;
import Api_de_zoologico.zoo.services.VeterinarioService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veterinarios")
@CrossOrigin(origins = "*")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) String nome) {
        try {
            if (especialidade != null && !especialidade.trim().isEmpty()) {
                return ResponseEntity.ok(veterinarioService.findByEspecialidade(especialidade));
            }
            if (nome != null && !nome.trim().isEmpty()) {
                return ResponseEntity.ok(veterinarioService.findByNome(nome));
            }
            return ResponseEntity.ok(veterinarioService.findAll());
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar veterinários",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VeterinarioDto veterinarioDto) {
        Veterinario veterinarioCriado = veterinarioService.create(veterinarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioCriado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody VeterinarioDto veterinarioDto) {
        Veterinario veterinarioAtualizado = veterinarioService.update(id, veterinarioDto);
        return ResponseEntity.ok(veterinarioAtualizado);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        veterinarioService.delete(id);
        return ResponseEntity.ok(new MensagemResponse("Veterinário removido com sucesso"));
    }

    public record MensagemResponse(String mensagem) {}
}
