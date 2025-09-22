package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.FuncionarioRequestDto;
import Api_de_zoologico.zoo.dtos.FuncionarioResponseDto;
import Api_de_zoologico.zoo.models.Funcionario;
import Api_de_zoologico.zoo.services.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioResponseDto> criarFuncionario(@RequestBody FuncionarioRequestDto dto) {
        Funcionario funcionario = funcionarioService.criarFuncionario(dto);

        FuncionarioResponseDto response = new FuncionarioResponseDto();
        response.setId(funcionario.getId());
        response.setNome(funcionario.getNome());
        response.setCpf(funcionario.getCpf());
        response.setCargo(funcionario.getCargo());
        response.setTelefone(funcionario.getTelefone());
        response.setDataContratacao(funcionario.getDataContratacao());
        response.setUsername(funcionario.getUser().getUsername());

        return ResponseEntity.ok(response);
    }
}
