package orbezo.ms_bus.repository;

import orbezo.ms_bus.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    boolean existsByPlacaBus(String placaBus);
}