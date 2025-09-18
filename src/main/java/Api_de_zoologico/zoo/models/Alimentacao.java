package Api_de_zoologico.zoo.models;

import jakarta.persistence.*;
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

    private String tipoComida;
    private Double quantidadeDiaria;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
