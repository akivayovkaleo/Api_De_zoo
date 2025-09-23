package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.FuncionarioRequestDto;
import Api_de_zoologico.zoo.dtos.FuncionarioResponseDto;
import Api_de_zoologico.zoo.models.Funcionario;
import Api_de_zoologico.zoo.models.User;
import Api_de_zoologico.zoo.repositories.FuncionarioRepository;
import Api_de_zoologico.zoo.repositories.RoleRepository;
import Api_de_zoologico.zoo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Funcionario criarFuncionario(FuncionarioRequestDto dto) {
        if (funcionarioRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("Já existe funcionário com esse CPF");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_FUNCIONARIO")
                .orElseThrow(() -> new RuntimeException("Role ROLE_FUNCIONARIO não encontrada")));
        userRepository.save(user);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setCargo(dto.getCargo());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setDataContratacao(LocalDate.now());
        funcionario.setUser(user);

        return funcionarioRepository.save(funcionario);
    }

    public List<FuncionarioResponseDto> findAll() {
        return funcionarioRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public FuncionarioResponseDto findById(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        return toResponseDto(funcionario);
    }

    public FuncionarioResponseDto update(Long id, FuncionarioRequestDto dto) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setCargo(dto.getCargo());
        funcionario.setTelefone(dto.getTelefone());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            funcionario.getUser().setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        funcionarioRepository.save(funcionario);
        return toResponseDto(funcionario);
    }

    public void delete(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        funcionarioRepository.delete(funcionario);
    }

    private FuncionarioResponseDto toResponseDto(Funcionario funcionario) {
        FuncionarioResponseDto dto = new FuncionarioResponseDto();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setCpf(funcionario.getCpf());
        dto.setCargo(funcionario.getCargo());
        dto.setTelefone(funcionario.getTelefone());
        dto.setDataContratacao(funcionario.getDataContratacao());
        dto.setUsername(funcionario.getUser().getUsername());
        return dto;
    }
}
