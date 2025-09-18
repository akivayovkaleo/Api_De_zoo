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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        alimentServ.deletar(id);
    }

    @GetMapping("?={tipoComida}")
    public ResponseEntity<?> listarPorComida(@PathVariable String tipoComida) {
        try {
            return ResponseEntity.ok(alimentServ.buscarPorTipoComida(tipoComida));
        } catch (RuntimeException e) {
            return RespostaUtil.
                    buildErrorResponse("Falha ao encontrar comida",
                            e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("?={anima}")
    public ResponseEntity<?> listarPorAnima(@PathVariable String anima) {}
}
