package orbezo.usuario_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.usuario_service.dto.LoginRequestDTO;
import orbezo.usuario_service.dto.UsuarioRequestDTO;
import orbezo.usuario_service.dto.UsuarioResponseDTO;
import orbezo.usuario_service.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioByUsername(@PathVariable String username) {
        return ResponseEntity.ok(usuarioService.findByUsername(username));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO created = usuarioService.register(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/validar")
    public ResponseEntity<UsuarioResponseDTO> validarCredenciales(@RequestBody LoginRequestDTO loginRequest) {
        UsuarioResponseDTO usuario = usuarioService.validarCredenciales(loginRequest);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/auth/{username}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioForAuth(@PathVariable String username) {

        UsuarioResponseDTO usuario = usuarioService.findByUsername(username);

        return ResponseEntity.ok(usuario);
    }
}