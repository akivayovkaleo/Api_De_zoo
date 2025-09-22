package Api_de_zoologico.zoo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "veterinarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veterinario extends Funcionario {

    @Column(unique = true, length = 20)
    private String crmv;

    @Column(length = 50)
    private String especialidade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "funcionario_id", referencedColumnName = "id")
    private Funcionario funcionario;
}
