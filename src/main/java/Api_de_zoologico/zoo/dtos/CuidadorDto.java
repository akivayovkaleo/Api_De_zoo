package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CuidadorDto(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @Size(max = 50, message = "A especialidade deve ter no máximo 50 caracteres")
        String especialidade,

        @Size(max = 20, message = "O turno deve ter no máximo 20 caracteres")
        String turno,

        FuncionarioRequestDto funcionario // <---- vínculo com Funcionario
) {}
