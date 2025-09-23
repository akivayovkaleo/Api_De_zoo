package Api_de_zoologico.zoo.controllers;

import Api_de_zoologico.zoo.dtos.FuncionarioRequestDto;
import Api_de_zoologico.zoo.dtos.FuncionarioResponseDto;
import Api_de_zoologico.zoo.models.Funcionario;
import Api_de_zoologico.zoo.services.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<FuncionarioResponseDto> listar() {
        return funcionarioService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FuncionarioResponseDto buscarPorId(@PathVariable Long id) {
        return funcionarioService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FuncionarioResponseDto atualizar(@PathVariable Long id, @RequestBody FuncionarioRequestDto dto) {
        return funcionarioService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
