package Api_de_zoologico.zoo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "funcionarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, length = 11)
    private String cpf;

    private String cargo; // Ex: "Cuidador", "Veterinário", "Administrador"
    private String setor; // Ex: "Mamíferos", "Aves"
    private Double salario;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
