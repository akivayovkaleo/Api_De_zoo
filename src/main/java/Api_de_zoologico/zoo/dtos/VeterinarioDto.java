package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VeterinarioDto(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotBlank(message = "O CRMV é obrigatório")
        @Size(min = 3, max = 20, message = "O CRMV deve ter entre 3 e 20 caracteres")
        String crmv,

        @Size(max = 50, message = "A especialidade deve ter no máximo 50 caracteres")
        String especialidade,

        FuncionarioRequestDto funcionario // <---- vínculo com Funcionario
) {}
