package orbezo.ms_destino.repository;

import orbezo.ms_destino.entity.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
    boolean existsByCiuDest(String ciuDest);
}