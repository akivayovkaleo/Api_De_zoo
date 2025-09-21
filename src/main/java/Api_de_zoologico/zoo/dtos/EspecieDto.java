package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;

public record EspecieDto(
        @NotBlank(message = "Nome da especie é obrigatório")
        String nome,
        String descricao,
        String nomeCientifico,
        String familia,
        String ordem,
        String classe
) {}
