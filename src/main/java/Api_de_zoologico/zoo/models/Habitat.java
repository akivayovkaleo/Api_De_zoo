package Api_de_zoologico.zoo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "habitats")
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String nome;

    @Column(length = 45, nullable = false)
    private String tipo;

    private int capacidadeAnimal;

    @JsonIgnore
    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais;
}
