package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.AlimentacaoDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alimentacoes")
public class AlimentacaoController {

    private final AlimentacaoService alimentServ;

    public AlimentacaoController(AlimentacaoService alimentServ) {
        this.alimentServ = alimentServ;
    }

    @Operation(summary = "Cria um novo alimento")
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody AlimentacaoDto alimentacao,
            HttpServletRequest request
    ){
        Alimentacao created = alimentServ.create(alimentacao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespostaUtil.success(created, "Alimentação criada com sucesso", request.getRequestURI()));
    }

    @Operation(summary = "Busca um alimento pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "Id do alimento a ser retornado")
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Alimentacao alimentacao = alimentServ.findById(id); // lança NoSuchElementException se não existir
        return ResponseEntity.ok(
                RespostaUtil.success(alimentacao, "Alimentação retornada com sucesso", request.getRequestURI())
        );
    }

    @Operation(
            summary = "Lista todas as alimentações",
            description = "Retorna todas as alimentações ou filtra por tipo de comida e/ou por ID do animal."
    )
    @GetMapping
    public ResponseEntity<?> listar(
            @Parameter(description = "Tipo de comida (opcional)")
            @RequestParam(required = false) String tipoComida,
            @RequestParam(required = false) Long animal_id,
            HttpServletRequest request
    ) {
        List<Alimentacao> alimentacoes = alimentServ.getAll();
        String msg = "Lista de alimentações retornada com sucesso";

        if (tipoComida != null && !tipoComida.isEmpty()) {
            alimentacoes = alimentServ.buscarPorTipoComida(tipoComida);
            msg = "Lista filtrada por tipo de comida";
        }

        else if (animal_id != null) {
            alimentacoes = alimentServ.buscarPorAnimalId(animal_id);
            msg = "Lista filtrada por ID do animal";
        }

        if (alimentacoes.isEmpty()) {msg = "Lista vazia";}

        return ResponseEntity.ok(
                RespostaUtil.success(alimentacoes, msg, request.getRequestURI())
        );
    }

    @Operation(summary = "Atualiza um alimento")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Id do alimento a ser atualizado")
            @PathVariable Long id,
            @Valid @RequestBody AlimentacaoDto alimentacaoAtualizada,
            HttpServletRequest request
    ) {
        alimentServ.update(id, alimentacaoAtualizada);
        Alimentacao alimentacao = alimentServ.findById(id);
        return ResponseEntity.ok(
                RespostaUtil.success(alimentacao, "Alimentação atualizada com sucesso", request.getRequestURI())
        );
    }

    @Operation(summary = "Deleta um alimento")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Id do alimento a ser deletado")
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        alimentServ.deletar(id);
        return ResponseEntity.ok(
                RespostaUtil.success(null, "Alimentação deletada com sucesso", request.getRequestURI())
        );
    }
}
