package orbezo.auth_service.service;

import lombok.RequiredArgsConstructor;
import orbezo.auth_service.client.UsuarioFeignClient;
import orbezo.auth_service.dto.LoginRequestDTO;
import orbezo.auth_service.dto.LoginResponseDTO;
import orbezo.auth_service.dto.UsuarioDTO;
import orbezo.auth_service.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioFeignClient usuarioFeignClient;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        UsuarioDTO usuario = usuarioFeignClient.buscarPorUsername(loginRequest.getUsername());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        System.out.println("INPUT: " + loginRequest.getPassword());
        System.out.println("HASH: " + usuario.getPassword());
        System.out.println("MATCH: " + passwordEncoder.matches(
                loginRequest.getPassword(),
                usuario.getPassword()
        ));

        String token = jwtUtil.generateToken(usuario);

        return LoginResponseDTO.builder()
                .token(token)
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .roles(usuario.getRoles())
                .build();

    }
}