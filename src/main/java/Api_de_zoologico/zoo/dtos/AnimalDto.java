package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;

public record AnimalDto(
        @NotBlank(message = "O nome é obrigatório") String nome,
                        int idade,
        @NotBlank(message = "O habitat é obrigatório") Long habitat_id,
        @NotBlank(message = "A espécie é obrigatória") Long especie_id,
        @NotBlank(message = "O cuidador é obrigatório") Long cuidador_id,
        @NotBlank(message = "A alimentação é obrigatória") Long alimentacao_id) {
}
