package Api_de_zoologico.zoo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cuidadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuidador extends Funcionario {

    @Column(length = 50)
    private String especialidade;

    @Column(length = 20)
    private String turno;

    @OneToMany(mappedBy = "cuidador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "funcionario_id", referencedColumnName = "id")
    private Funcionario funcionario;
}
