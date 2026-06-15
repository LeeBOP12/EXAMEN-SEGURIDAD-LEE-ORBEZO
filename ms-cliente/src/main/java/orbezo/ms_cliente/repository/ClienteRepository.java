package orbezo.ms_cliente.repository;

import orbezo.ms_cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNomCliAndApeCli(String nomCli, String apeCli);
}