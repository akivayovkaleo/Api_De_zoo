package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Alimentacao;
import Api_de_zoologico.zoo.services.AlimentacaoService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/alimentacoes")
public class AlimentacaoController {
    private AlimentacaoService alimentServ;

    public AlimentacaoController(AlimentacaoService alimentServ) {
        this.alimentServ = alimentServ;
    }

    @PostMapping
    public ResponseEntity<Alimentacao> create(@RequestBody Alimentacao alimentacao){
        return ResponseEntity.ok(alimentServ.create(alimentacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(alimentServ.findById(id));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Alimento não encontrado",
                            e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(alimentServ.getAll());
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao listar alimentos",
                            e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Alimentacao alimentacao) {
        try {
            return ResponseEntity.ok(alimentServ.update(id, alimentacao));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao encontrar alimento",
                            e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        alimentServ.deletar(id);
    }

    @GetMapping("?tipoComida={tipoComida}")
    public ResponseEntity<?> listarPorComida(@PathVariable String tipoComida) {
        try {
            return ResponseEntity.ok(alimentServ.buscarPorTipoComida(tipoComida));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao encontrar comida",
                            e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("?animalId={animal_id}")
    public ResponseEntity<?> listarPorAnimalId(@PathVariable Long animal_id) {
        try {
            return ResponseEntity.ok(alimentServ.buscarPorAnimalId(animal_id));
        } catch (RuntimeException e) {
            return RespostaUtil.buildErrorResponse("Animal não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
