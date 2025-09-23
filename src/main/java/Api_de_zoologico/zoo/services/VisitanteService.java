package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.VisitanteRequestDto;
import Api_de_zoologico.zoo.dtos.VisitanteResponseDto;
import Api_de_zoologico.zoo.models.User;
import Api_de_zoologico.zoo.models.Visitante;
import Api_de_zoologico.zoo.repositories.UserRepository;
import Api_de_zoologico.zoo.repositories.RoleRepository;
import Api_de_zoologico.zoo.repositories.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitanteService {

    private final VisitanteRepository visitanteRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public VisitanteResponseDto criarVisitante(VisitanteRequestDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(roleRepository.findByName("ROLE_VISITANTE")
                .orElseThrow(() -> new RuntimeException("Role ROLE_VISITANTE não encontrada")));
        userRepository.save(user);

        Visitante visitante = new Visitante();
        visitante.setNome(dto.nome());
        visitante.setIdade(dto.idade());
        visitante.setDocumento(dto.documento());
        visitante.setUser(user);

        visitanteRepository.save(visitante);

        return toResponseDto(visitante);
    }

    public List<VisitanteResponseDto> findAll() {
        return visitanteRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public VisitanteResponseDto findById(Long id) {
        Visitante visitante = visitanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitante não encontrado com ID: " + id));
        return toResponseDto(visitante);
    }

    public VisitanteResponseDto update(Long id, VisitanteRequestDto dto) {
        Visitante visitante = visitanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitante não encontrado com ID: " + id));

        visitante.setNome(dto.nome());
        visitante.setIdade(dto.idade());
        visitante.setDocumento(dto.documento());

        if (dto.password() != null && !dto.password().isBlank()) {
            visitante.getUser().setPassword(passwordEncoder.encode(dto.password()));
        }

        visitanteRepository.save(visitante);
        return toResponseDto(visitante);
    }

    public void delete(Long id) {
        Visitante visitante = visitanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitante não encontrado com ID: " + id));
        visitanteRepository.delete(visitante);
    }

    private VisitanteResponseDto toResponseDto(Visitante visitante) {
        return new VisitanteResponseDto(
                visitante.getId(),
                visitante.getNome(),
                visitante.getIdade(),
                visitante.getDocumento(),
                visitante.getUser().getUsername()
        );
    }
}