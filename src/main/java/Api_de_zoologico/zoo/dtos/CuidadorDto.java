package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CuidadorDto(
        @NotBlank(message = "O nome do cuidador é obrigatório") String nome,
        String especialidade,
        String turno,
        @NotBlank(message = "O email do cuidador é obrigatório") @Email String email) {
}
