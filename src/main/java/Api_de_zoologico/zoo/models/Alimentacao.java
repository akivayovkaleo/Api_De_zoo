package Api_de_zoologico.zoo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_comida", length = 100)
    private String tipoComida;

    @Column(name = "quantidade_diaria")
    private Double quantidadeDiaria;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonIgnore
    private Animal animal;
}