package Api_de_zoologico.zoo.config;

import Api_de_zoologico.zoo.models.Role;
import Api_de_zoologico.zoo.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        criarRoleSeNaoExistir("ROLE_VISITANTE");
        criarRoleSeNaoExistir("ROLE_FUNCIONARIO");
        criarRoleSeNaoExistir("ROLE_ADMIN");
        criarRoleSeNaoExistir("ROLE_CUIDADOR");
        criarRoleSeNaoExistir("ROLE_VETERINARIO");
    }

    private void criarRoleSeNaoExistir(String name) {
        roleRepository.findByName(name).ifPresentOrElse(
                role -> {},
                () -> {
                    Role role = new Role();
                    role.setName(name);
                    roleRepository.save(role);
                    System.out.println("Criada role: " + name);
                }
        );
    }
}
