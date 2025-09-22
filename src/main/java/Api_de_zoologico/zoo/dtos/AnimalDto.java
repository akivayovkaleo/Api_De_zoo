package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AnimalDto(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        int idade,

        @NotNull(message = "O habitat é obrigatório")
        @Positive(message = "O habitat deve ser um ID positivo")
        Long habitat_id,

        @NotNull(message = "A espécie é obrigatória")
        @Positive(message = "A espécie deve ser um ID positivo")
        Long especie_id,

        @NotNull(message = "O cuidador é obrigatório")
        @Positive(message = "O cuidador deve ser um ID positivo")
        Long cuidador_id,

        @NotNull(message = "A alimentação é obrigatória")
        @Positive(message = "A alimentação deve ser um ID positivo")
        Long alimentacao_id )
{
}
