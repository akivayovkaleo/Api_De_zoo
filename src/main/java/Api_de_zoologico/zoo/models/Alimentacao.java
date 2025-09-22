package Api_de_zoologico.zoo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alimentacoes")
public class Alimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O tipo de comida não pode ser vazio")
    private String tipoComida;

    @NotNull(message = "A quantidade diária é obrigatória")
    @Positive(message = "A quantidade diária deve ser positiva")
    private Double quantidadeDiaria;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
