package orbezo.ms_programacion.repository;

import orbezo.ms_programacion.entity.Programacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ProgramacionRepository extends JpaRepository<Programacion, Long> {
    List<Programacion> findByIdBus(Long idBus);
    List<Programacion> findByFecha(LocalDate fecha);
    List<Programacion> findByIdBusAndFecha(Long idBus, LocalDate fecha);
    boolean existsByIdBusAndFechaAndHora(Long idBus, LocalDate fecha, LocalTime hora);
}