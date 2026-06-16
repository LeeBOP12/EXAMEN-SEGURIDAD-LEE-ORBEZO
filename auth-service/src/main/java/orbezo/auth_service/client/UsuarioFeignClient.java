package orbezo.auth_service.client;

import orbezo.auth_service.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")
public interface UsuarioFeignClient {

    @GetMapping("/api/usuarios/username/{username}")
    UsuarioDTO getUsuarioByUsername(@PathVariable("username") String username);
}