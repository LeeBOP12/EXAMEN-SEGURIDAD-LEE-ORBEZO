package orbezo.usuario_service.repository;

import orbezo.usuario_service.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithRoles(@Param("username") String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}