package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.FuncionarioRequestDto;
import Api_de_zoologico.zoo.dtos.FuncionarioResponseDto;
import Api_de_zoologico.zoo.models.Funcionario;
import Api_de_zoologico.zoo.models.Role;
import Api_de_zoologico.zoo.models.User;
import Api_de_zoologico.zoo.repositories.FuncionarioRepository;
import Api_de_zoologico.zoo.repositories.RoleRepository;
import Api_de_zoologico.zoo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioResponseDto criarFuncionario(FuncionarioRequestDto dto) {
        if (funcionarioRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("Já existe funcionário com esse CPF");
        }

        // Criar User
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.getRoles().add(roleRepository.findByName("ROLE_FUNCIONARIO")
                .orElseThrow(() -> new RuntimeException("Role não encontrada")));
        userRepository.save(user);

        // Criar Funcionario
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSetor(dto.getSetor());
        funcionario.setSalario(dto.getSalario());
        funcionario.setUser(user);
        funcionarioRepository.save(funcionario);

        // Converter para DTO de resposta
        FuncionarioResponseDto response = new FuncionarioResponseDto();
        response.setId(funcionario.getId());
        response.setNome(funcionario.getNome());
        response.setCpf(funcionario.getCpf());
        response.setCargo(funcionario.getCargo());
        response.setSetor(funcionario.getSetor());
        response.setSalario(funcionario.getSalario());
        response.setUsername(user.getUsername());

        return response;
    }
}
