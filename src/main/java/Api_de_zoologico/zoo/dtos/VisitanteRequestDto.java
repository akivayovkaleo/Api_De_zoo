package Api_de_zoologico.zoo.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VisitanteRequestDto {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;

    private String username;
    private String password;
}