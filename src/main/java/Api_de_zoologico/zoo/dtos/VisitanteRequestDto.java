package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VisitanteRequestDto(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Idade é obrigatória")
        @Min(value = 0, message = "Idade não pode ser negativa")
        Integer idade,

        @NotBlank(message = "Documento é obrigatório")
        String documento,

        @NotBlank(message = "Username é obrigatório")
        String username,

        @NotBlank(message = "Senha é obrigatória")
        String password
) {}