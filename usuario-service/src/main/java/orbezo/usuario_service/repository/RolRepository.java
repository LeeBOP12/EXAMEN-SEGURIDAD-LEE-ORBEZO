package orbezo.usuario_service.repository;

import orbezo.usuario_service.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);

    // ✅ AGREGAR ESTE MÉTODO
    boolean existsByNombre(String nombre);
}