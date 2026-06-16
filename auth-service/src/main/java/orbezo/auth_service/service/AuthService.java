package orbezo.auth_service.service;

import lombok.RequiredArgsConstructor;
import orbezo.auth_service.client.UsuarioFeignClient;
import orbezo.auth_service.dto.LoginRequestDTO;
import orbezo.auth_service.dto.UsuarioDTO;
import orbezo.auth_service.dto.ValidateTokenDTO;
import orbezo.auth_service.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioFeignClient usuarioFeignClient;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;  // ✅ INYECTAR (no crear con new)

    public Map<String, Object> login(LoginRequestDTO loginRequest) {
        UsuarioDTO usuario;
        try {
            usuario = usuarioFeignClient.getUsuarioByUsername(loginRequest.getUsername());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        System.out.println("===== LOGIN =====");
        System.out.println("Username: " + loginRequest.getUsername());
        System.out.println("Password enviada: " + loginRequest.getPassword());
        System.out.println("Password BD: " + usuario.getPassword());
        System.out.println("=================");

        // ✅ Validar contraseña con BCrypt
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        String rol = usuario.getRoles().stream()
                .findFirst()
                .orElse("USER");

        String token = jwtUtil.generateToken(usuario.getIdUsuario(), usuario.getUsername(), rol);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", usuario.getUsername());
        response.put("roles", usuario.getRoles());
        response.put("userId", usuario.getIdUsuario());
        response.put("expiresIn", jwtUtil.getExpiration());

        return response;
    }

    public ValidateTokenDTO validateToken(String token) {
        ValidateTokenDTO response = new ValidateTokenDTO();

        if (token == null || token.isEmpty()) {
            response.setValid(false);
            return response;
        }

        if (jwtUtil.validateToken(token)) {
            response.setValid(true);
            response.setUserId(jwtUtil.extractUserId(token));
            response.setUsername(jwtUtil.extractUsername(token));
            response.setRol(jwtUtil.extractRol(token));
        } else {
            response.setValid(false);
        }

        return response;
    }
}