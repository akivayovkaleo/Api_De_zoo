package Api_de_zoologico.zoo.dtos;

import lombok.Data;

@Data
public class FuncionarioRequestDto {
    private String nome;
    private String cpf;
    private String cargo;
    private String setor;
    private Double salario;

    private String username;
    private String password;
}
