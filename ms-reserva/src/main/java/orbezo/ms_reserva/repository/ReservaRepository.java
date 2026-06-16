package orbezo.ms_reserva.repository;

import orbezo.ms_reserva.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByIdCliente(Long idCliente);
    List<Reserva> findByIdProgramacion(Long idProgramacion);
    List<Reserva> findByFechaReser(LocalDate fechaReser);
    List<Reserva> findByIdClienteAndFechaReserBetween(Long idCliente, LocalDate startDate, LocalDate endDate);
}
