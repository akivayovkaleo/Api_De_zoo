package Api_de_zoologico.zoo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuidadores")
public class Cuidador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 50)
    private String especialidade;

    @Column(length = 20)
    private String turno;

    @JsonIgnore
    @OneToMany(mappedBy = "cuidador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais;

    // Relacionamento com Funcionario
    @OneToOne
    @JoinColumn(name = "funcionario_id", referencedColumnName = "id")
    private Funcionario funcionario;
}
