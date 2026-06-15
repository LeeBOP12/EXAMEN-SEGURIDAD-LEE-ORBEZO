package orbezo.ms_asiento.repository;

import orbezo.ms_asiento.entity.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {
    List<Asiento> findByIdBus(Long idBus);
    boolean existsByNumAsiAndIdBus(String numAsi, Long idBus);
}