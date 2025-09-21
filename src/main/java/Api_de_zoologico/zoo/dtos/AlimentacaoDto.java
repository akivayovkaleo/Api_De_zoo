package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AlimentacaoDto(
        @NotBlank(message = "O tipo de comida é obrigatório")
        String tipoComida,

        @NotNull(message = "A quantidade diária é obrigatória")
        @Positive(message = "A quantidade diária deve ser um número positivo")
        Double quantidadeDiaria,

        @NotNull(message = "O ID do animal é obrigatório")
        Long animal_id
) {
}