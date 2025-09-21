package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EspecieDto(
        @NotBlank(message = "O nome da espécie é obrigatório")
        @Size(min = 2, max = 100, message = "O nome da espécie deve ter entre 2 e 100 caracteres")
        String nome,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String descricao,

        @Size(max = 100, message = "O nome científico deve ter no máximo 100 caracteres")
        String nomeCientifico,

        @Size(max = 50, message = "A família deve ter no máximo 50 caracteres")
        String familia,

        @Size(max = 50, message = "A ordem deve ter no máximo 50 caracteres")
        String ordem,

        @Size(max = 50, message = "A classe deve ter no máximo 50 caracteres")
        String classe
) {
}