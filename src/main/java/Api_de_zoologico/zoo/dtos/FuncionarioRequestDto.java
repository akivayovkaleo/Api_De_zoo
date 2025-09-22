package Api_de_zoologico.zoo.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioRequestDto {
    private String nome;
    private String telefone;
    private String cpf;
    private String cargo;
    private String setor;
    private Double salario;
    private LocalDate dataContratacao;

    private String username;
    private String password;
}
