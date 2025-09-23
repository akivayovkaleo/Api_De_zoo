package Api_de_zoologico.zoo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "visitantes")
@Getter
@Setter
@NoArgsConstructor
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false, unique = true)
    private String documento;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Visitante(String nome, Integer idade, String documento, User user) {
        this.nome = nome;
        this.idade = idade;
        this.documento = documento;
        this.user = user;
    }
}