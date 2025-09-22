package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.models.Alimentacao;
import Api_de_zoologico.zoo.services.AlimentacaoService;
import Api_de_zoologico.zoo.utils.RespostaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/alimentacoes")
public class AlimentacaoController {
    private AlimentacaoService alimentServ;

    public AlimentacaoController(AlimentacaoService alimentServ) {
        this.alimentServ = alimentServ;
    }

    @Operation(
            summary = "Cria um novo alimento"
    )
    @PostMapping
    public ResponseEntity<Alimentacao> create(@Valid @RequestBody Alimentacao alimentacao){
        return ResponseEntity.ok(alimentServ.create(alimentacao));
    }

    @Operation(
            summary = "Busca um alimento pelo Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "Id do alimento a ser retornado")
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Alimentacao alimentacao = alimentServ.findById(id);
        return ResponseEntity.ok(RespostaUtil.success(alimentacao, "Cliente retornado com sucesso", request.getRequestURI()));
    }

    @Operation(
            summary = "Lista todas as alimentações",
            description = "Retorna a lista de alimentações. " +
                    "É possível filtrar por tipo de comida e/ou por ID do animal. " +
                    "Se nenhum parâmetro for fornecido, retorna todas as alimentações."
    )
    @GetMapping
    public ResponseEntity<?> listar(
            @Parameter(description = "Tipo de comida (opcional)")
            @RequestParam(required = false) String tipoComida,
            @RequestParam(required = false) Long animal_id,
            HttpServletRequest request
    ) {
        List<Alimentacao> alimentacoes;

        if (tipoComida != null && !tipoComida.isEmpty()) {
            alimentacoes = alimentServ.buscarPorTipoComida(tipoComida);
        } else if (animal_id != null) {
            alimentacoes = alimentServ.buscarPorAnimalId(animal_id);
        } else {
            alimentacoes = alimentServ.getAll();
        }

        return ResponseEntity.ok( RespostaUtil.success(
                alimentacoes,
                "Lista de alimentações retornada com sucesso",
                request.getRequestURI())
        );
    }

    @Operation(
            summary = "Atualiza um alimento"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Id do alimento a ser atualizado")
            @PathVariable Long id,
            @Valid @RequestBody Alimentacao alimentacaoAtualizada,
            HttpServletRequest request
    ) {
        alimentServ.update(id, alimentacaoAtualizada);
        Alimentacao alimentacao = alimentServ.findById(id);
        return ResponseEntity.ok(RespostaUtil.success(alimentacao, "Alimentação atualizada com sucesso", request.getRequestURI()));
    }

    @Operation(
            summary = "Deleta um alimento"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Id do alimento a ser deletado")
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        alimentServ.deletar(id);
        return ResponseEntity.ok(RespostaUtil.success(null, "Alimentação deletada com sucesso", request.getRequestURI()));
    }

//    @Operation(summary = "Busca todos os alimentos por tipo de comida")
//    @GetMapping("/tipoComida")
//    public ResponseEntity<?> listarPorComida(
//            @Parameter(description = "Tipo de comida a ser buscado")
//            @RequestParam String tipoComida,
//            HttpServletRequest request
//    ) {
//        List<Alimentacao> alimentacoes = alimentServ.buscarPorTipoComida(tipoComida);
//        return ResponseEntity.ok(RespostaUtil.success(alimentacoes, "Lista de alimentações retornada com sucesso", request.getRequestURI()));
//    }
//
//    @Operation(summary = "Busca todos os alimentos por animal")
//    @GetMapping("/animal")
//    public ResponseEntity<?> listarPorAnimalId(
//            @Parameter(description = "Id do animal a ser buscado")
//            @RequestParam Long animal_id,
//            HttpServletRequest request
//    ) {
//        List<Alimentacao> alimentacoes = alimentServ.buscarPorAnimalId(animal_id);
//        return ResponseEntity.ok(RespostaUtil.success(alimentacoes, "Lista de alimentações retornada com sucesso", request.getRequestURI()));
//    }
}
