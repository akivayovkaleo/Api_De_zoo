package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.RoleRequestDto;
import Api_de_zoologico.zoo.dtos.RoleResponseDto;
import Api_de_zoologico.zoo.models.Role;
import Api_de_zoologico.zoo.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleResponseDto criarRole(RoleRequestDto dto) {
        if (roleRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Role já existe: " + dto.getName());
        }

        Role role = new Role();
        role.setName(dto.getName());
        roleRepository.save(role);

        RoleResponseDto response = new RoleResponseDto();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }

    public List<RoleResponseDto> listarRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> {
                    RoleResponseDto dto = new RoleResponseDto();
                    dto.setId(role.getId());
                    dto.setName(role.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
