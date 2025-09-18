package Api_de_zoologico.zoo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoDto {
    @NotBlank(message = "Nome do evento é obrigatório")
    private String nomeDoEvento;

    private String descricao;

    @NotNull(message = "Data e hora do evento são obrigatórias")
    private LocalDateTime dataHora;

    @NotNull(message = "ID do habitat é obrigatório")
    private Long localHabitatId;
}