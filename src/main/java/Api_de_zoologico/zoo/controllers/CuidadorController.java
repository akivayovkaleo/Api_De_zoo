package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.CuidadorDto;
import Api_de_zoologico.zoo.models.Cuidador;
import Api_de_zoologico.zoo.services.CuidadorService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuidadores")
@CrossOrigin(origins = "*")
public class CuidadorController {

    private final CuidadorService cuidadorService;

    public CuidadorController(CuidadorService cuidadorService) {
        this.cuidadorService = cuidadorService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) String turno,
            @RequestParam(required = false) String nome) {
        try {
            if (especialidade != null && !especialidade.trim().isEmpty()) {
                return ResponseEntity.ok(cuidadorService.findByEspecialidade(especialidade));
            }
            if (turno != null && !turno.trim().isEmpty()) {
                return ResponseEntity.ok(cuidadorService.findByTurno(turno));
            }
            if (nome != null && !nome.trim().isEmpty()) {
                return ResponseEntity.ok(cuidadorService.findByNome(nome));
            }
            return ResponseEntity.ok(cuidadorService.findAll());
        } catch (Exception e) {
            return RespostaUtil.buildErrorResponse(
                    "Erro ao buscar cuidadores",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cuidadorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CuidadorDto cuidadorDto) {
        Cuidador cuidadorCriado = cuidadorService.create(cuidadorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuidadorCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody CuidadorDto cuidadorDto) {
        Cuidador cuidadorAtualizado = cuidadorService.update(id, cuidadorDto);
        return ResponseEntity.ok(cuidadorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        cuidadorService.delete(id);
        return ResponseEntity.ok(new MensagemResponse("Cuidador removido com sucesso"));
    }

    public record MensagemResponse(String mensagem) {}
}
