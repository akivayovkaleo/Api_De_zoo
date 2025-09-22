package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.FuncionarioRequestDto;
import Api_de_zoologico.zoo.dtos.FuncionarioResponseDto;
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
        return ResponseEntity.ok(funcionarioService.criarFuncionario(dto));
    }
}
