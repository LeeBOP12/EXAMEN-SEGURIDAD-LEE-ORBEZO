package orbezo.auth_service.client;

import orbezo.auth_service.dto.LoginRequestDTO;
import orbezo.auth_service.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "usuario-service")
public interface UsuarioFeignClient {

    @PostMapping("/api/usuarios/validar")
    UsuarioDTO validarCredenciales(@RequestBody LoginRequestDTO loginRequest);

    @GetMapping("/api/usuarios/username/{username}")
    UsuarioDTO buscarPorUsername(@PathVariable String username);
}