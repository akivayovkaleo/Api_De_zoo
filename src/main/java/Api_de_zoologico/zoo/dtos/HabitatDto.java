package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HabitatDto(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O tipo é obrigatório")
        String tipo,

        @NotNull(message = "A capacidade de animais é obrigatória")
        @Positive(message = "A capacidade deve ser um número positivo")
        Integer capacidadeAnimal
) {
}