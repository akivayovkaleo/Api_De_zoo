package Api_de_zoologico.zoo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visitantes")
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // dados específicos do visitante
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, length = 11)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(length = 20)
    private String telefone;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    // eventos em que o visitante se inscreveu
    @JsonIgnore
    @ManyToMany(mappedBy = "visitantes")
    private List<Evento> eventos;

    // Relacionamento com User (login/senha/roles)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
