package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;

public record HabitatDto(@NotBlank(message = "O nome é obrigatório") String nome,
                         @NotBlank(message = "O tipo é obrigatório") String tipo,
                         int capacidadeAnimal) {
}
