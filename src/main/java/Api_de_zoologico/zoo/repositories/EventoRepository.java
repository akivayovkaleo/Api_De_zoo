package Api_de_zoologico.zoo.repositories;

import Api_de_zoologico.zoo.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNomeContainingIgnoreCase(String nome);
    List<Evento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Evento> findByDataHoraAfter(LocalDateTime data);
    List<Evento> findByDataHoraBefore(LocalDateTime data);

    @Query("SELECT COUNT(e) FROM Evento e WHERE e.capacidadeMaxima = ?1")
    long countByCapacidadeMaxima(Integer capacidade);

    @Query("SELECT e FROM Evento e WHERE SIZE(e.visitantes) >= e.capacidadeMaxima")
    List<Evento> findEventosLotados();
}