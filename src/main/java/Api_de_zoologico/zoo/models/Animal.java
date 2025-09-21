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
@Table(name = "animais")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private int idade;

    @ManyToOne
    @JoinColumn(name = "habitat_id")
    @JsonIgnore
    private Habitat habitat;

    @ManyToOne
    @JoinColumn(name = "cuidador_id")
    @JsonIgnore
    private Cuidador cuidador;

    @JsonIgnore
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alimentacao> alimentacoes;

    @ManyToOne
    @JoinColumn(name = "especie_id")
    @JsonIgnore
    private Especie especie;
}