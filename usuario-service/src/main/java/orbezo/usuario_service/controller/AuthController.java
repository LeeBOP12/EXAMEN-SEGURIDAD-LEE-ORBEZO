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
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    // ===== Auth =====
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    // ===== CRUD Usuarios =====
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/usuarios/username/{username}")
    public ResponseEntity<UsuarioResponseDTO> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(usuarioService.findByUsername(username));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(requestDTO));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO requestDTO) {
        return ResponseEntity.ok(usuarioService.update(id, requestDTO));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}