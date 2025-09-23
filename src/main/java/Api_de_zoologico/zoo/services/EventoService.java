package Api_de_zoologico.zoo.services;

import Api_de_zoologico.zoo.dtos.EventoDto;
import Api_de_zoologico.zoo.models.Evento;
import Api_de_zoologico.zoo.models.Visitante;
import Api_de_zoologico.zoo.repositories.EventoRepository;
import Api_de_zoologico.zoo.repositories.VisitanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EventoService {
    private final EventoRepository eventoRepository;
    private final VisitanteRepository visitanteRepository;

    public EventoService(EventoRepository eventoRepository, VisitanteRepository visitanteRepository) {
        this.eventoRepository = eventoRepository;
        this.visitanteRepository = visitanteRepository;
    }

    public List<Evento> findAll() {
        try {
            return eventoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os eventos: " + e.getMessage());
        }
    }

    public Evento findById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
    }

    public List<Evento> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome não pode ser vazio ou nulo");
        }
        try {
            return eventoRepository.findByNomeContainingIgnoreCase(nome.trim());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar eventos pelo nome '" + nome + "': " + e.getMessage());
        }
    }

    public List<Evento> findByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new RuntimeException("Datas de início e fim são obrigatórias");
        }
        if (dataInicio.isAfter(dataFim)) {
            throw new RuntimeException("Data de início não pode ser posterior à data de fim");
        }
        try {
            return eventoRepository.findByDataHoraBetween(dataInicio, dataFim);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar eventos pelo período: " + e.getMessage());
        }
    }

    public List<Evento> findEventosFuturos() {
        try {
            return eventoRepository.findByDataHoraAfter(LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar eventos futuros: " + e.getMessage());
        }
    }

    public List<Evento> findEventosLotados() {
        try {
            return eventoRepository.findEventosLotados();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar eventos lotados: " + e.getMessage());
        }
    }

    public Evento create(EventoDto eventoDto) {
        try {
            if (eventoDto.nome() == null || eventoDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do evento é obrigatório");
            }

            if (eventoDto.dataHora() == null) {
                throw new RuntimeException("Data e hora do evento são obrigatórias");
            }

            if (eventoDto.capacidadeMaxima() == null || eventoDto.capacidadeMaxima() <= 0) {
                throw new RuntimeException("Capacidade máxima deve ser um número positivo");
            }

            if (eventoDto.dataHora().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("A data do evento deve ser futura");
            }

            Evento evento = new Evento();
            evento.setNome(eventoDto.nome().trim());
            evento.setDescricao(eventoDto.descricao() != null ? eventoDto.descricao().trim() : null);
            evento.setDataHora(eventoDto.dataHora());
            evento.setCapacidadeMaxima(eventoDto.capacidadeMaxima());
            evento.setDataCadastro(LocalDateTime.now());

            if (eventoDto.visitantesIds() != null && !eventoDto.visitantesIds().isEmpty()) {
                List<Visitante> visitantes = visitanteRepository.findAllById(eventoDto.visitantesIds());
                if (visitantes.size() != eventoDto.visitantesIds().size()) {
                    throw new RuntimeException("Um ou mais visitantes não foram encontrados");
                }
                evento.setVisitantes(visitantes);
            }

            return eventoRepository.save(evento);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar evento: " + e.getMessage());
        }
    }

    public Evento update(Long id, EventoDto eventoDto) {
        try {
            Evento eventoExistente = findById(id);

            if (eventoDto.nome() == null || eventoDto.nome().trim().isEmpty()) {
                throw new RuntimeException("Nome do evento é obrigatório");
            }

            if (eventoDto.dataHora() == null) {
                throw new RuntimeException("Data e hora do evento são obrigatórias");
            }

            if (eventoDto.capacidadeMaxima() == null || eventoDto.capacidadeMaxima() <= 0) {
                throw new RuntimeException("Capacidade máxima deve ser um número positivo");
            }

            if (eventoDto.dataHora().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("A data do evento deve ser futura");
            }

            if (eventoExistente.getVisitantes() != null &&
                    eventoDto.capacidadeMaxima() < eventoExistente.getVisitantes().size()) {
                throw new RuntimeException(
                        "Não é possível reduzir a capacidade para " + eventoDto.capacidadeMaxima() +
                                " pois o evento já possui " + eventoExistente.getVisitantes().size() + " inscritos"
                );
            }

            eventoExistente.setNome(eventoDto.nome().trim());
            eventoExistente.setDescricao(eventoDto.descricao() != null ? eventoDto.descricao().trim() : null);
            eventoExistente.setDataHora(eventoDto.dataHora());
            eventoExistente.setCapacidadeMaxima(eventoDto.capacidadeMaxima());

            if (eventoDto.visitantesIds() != null) {
                List<Visitante> visitantes = visitanteRepository.findAllById(eventoDto.visitantesIds());
                if (visitantes.size() != eventoDto.visitantesIds().size()) {
                    throw new RuntimeException("Um ou mais visitantes não foram encontrados");
                }
                eventoExistente.setVisitantes(visitantes);
            }

            return eventoRepository.save(eventoExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar evento com ID " + id + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            Evento evento = findById(id);
            eventoRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao remover evento com ID " + id + ": " + e.getMessage());
        }
    }

    public Evento adicionarVisitante(Long eventoId, Long visitanteId) {
        try {
            Evento evento = findById(eventoId);
            Visitante visitante = visitanteRepository.findById(visitanteId)
                    .orElseThrow(() -> new RuntimeException("Visitante não encontrado"));

            if (evento.getVisitantes() != null &&
                    evento.getVisitantes().size() >= evento.getCapacidadeMaxima()) {
                throw new RuntimeException("Evento já está lotado. Capacidade máxima: " + evento.getCapacidadeMaxima());
            }

            if (evento.getVisitantes() != null &&
                    evento.getVisitantes().stream().anyMatch(v -> v.getId().equals(visitanteId))) {
                throw new RuntimeException("Visitante já está inscrito neste evento");
            }

            evento.getVisitantes().add(visitante);
            return eventoRepository.save(evento);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar visitante ao evento: " + e.getMessage());
        }
    }

    public Evento removerVisitante(Long eventoId, Long visitanteId) {
        try {
            Evento evento = findById(eventoId);

            if (evento.getVisitantes() == null || evento.getVisitantes().isEmpty()) {
                throw new RuntimeException("Não há visitantes inscritos neste evento");
            }

            boolean removido = evento.getVisitantes().removeIf(v -> v.getId().equals(visitanteId));
            if (!removido) {
                throw new RuntimeException("Visitante não encontrado na lista de inscritos");
            }

            return eventoRepository.save(evento);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover visitante do evento: " + e.getMessage());
        }
    }
}