package orbezo.usuario_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.usuario_service.dto.RolRequestDTO;
import orbezo.usuario_service.dto.RolResponseDTO;
import orbezo.usuario_service.entity.Rol;
import orbezo.usuario_service.repository.RolRepository;
import orbezo.usuario_service.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> getAllRoles() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RolResponseDTO> getRolByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(rolService.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> createRol(@Valid @RequestBody RolRequestDTO request) {
        RolResponseDTO created = rolService.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> updateRol(
            @PathVariable Long id,
            @Valid @RequestBody RolRequestDTO request) {
        return ResponseEntity.ok(rolService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}