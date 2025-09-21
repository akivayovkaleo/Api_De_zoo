package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record EventoDto(
        @NotBlank(message = "O nome do evento é obrigatório")
        String nome,

        String descricao,

        @NotNull(message = "A data e hora do evento são obrigatórias")
        @Future(message = "A data do evento deve ser futura")
        LocalDateTime dataHora,

        @NotNull(message = "A capacidade máxima é obrigatória")
        @Positive(message = "A capacidade máxima deve ser um número positivo")
        Integer capacidadeMaxima,

        List<Long> visitantesIds
) {
}