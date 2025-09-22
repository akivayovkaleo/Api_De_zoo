package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;

public record VeterinarioDto (
        @NotBlank(message = "O nome do veterinário é obrigatório")String nome,
        String especialidade,
        String crmv){
}
