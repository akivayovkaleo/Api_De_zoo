package Api_de_zoologico.zoo.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VisitanteResponseDto {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private LocalDate dataCadastro;
    private String username;
}