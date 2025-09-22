package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.VisitanteRequestDto;
import Api_de_zoologico.zoo.dtos.VisitanteResponseDto;
import Api_de_zoologico.zoo.models.Role;
import Api_de_zoologico.zoo.models.User;
import Api_de_zoologico.zoo.models.Visitante;
import Api_de_zoologico.zoo.repositories.RoleRepository;
import Api_de_zoologico.zoo.repositories.UserRepository;
import Api_de_zoologico.zoo.repositories.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VisitanteService {

    private final VisitanteRepository visitanteRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public VisitanteResponseDto criarVisitante(VisitanteRequestDto dto) {
        if (visitanteRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("Já existe visitante com esse CPF");
        }

        // Criar User
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_VISITANTE")
                .orElseThrow(() -> new RuntimeException("Role ROLE_VISITANTE não encontrada")));
        userRepository.save(user);

        // Criar Visitante
        Visitante visitante = new Visitante();
        visitante.setNome(dto.getNome());
        visitante.setCpf(dto.getCpf());
        visitante.setDataNascimento(dto.getDataNascimento());
        visitante.setTelefone(dto.getTelefone());
        visitante.setDataCadastro(LocalDate.now());
        visitante.setUser(user);
        visitanteRepository.save(visitante);

        // Response
        VisitanteResponseDto response = new VisitanteResponseDto();
        response.setId(visitante.getId());
        response.setNome(visitante.getNome());
        response.setCpf(visitante.getCpf());
        response.setDataNascimento(visitante.getDataNascimento());
        response.setTelefone(visitante.getTelefone());
        response.setDataCadastro(visitante.getDataCadastro());
        response.setUsername(user.getUsername());

        return response;
    }
}