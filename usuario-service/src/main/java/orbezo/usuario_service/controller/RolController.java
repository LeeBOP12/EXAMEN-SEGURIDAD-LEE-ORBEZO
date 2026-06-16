package orbezo.usuario_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.usuario_service.entity.Rol;
import orbezo.usuario_service.repository.RolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolRepository rolRepository;

    @GetMapping
    public ResponseEntity<List<Rol>> getAll() {
        return ResponseEntity.ok(rolRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id)));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Rol> getByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre)));
    }

    @PostMapping
    public ResponseEntity<Rol> create(@Valid @RequestBody Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new RuntimeException("El rol '" + rol.getNombre() + "' ya existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rolRepository.save(rol));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> update(@PathVariable Long id, @Valid @RequestBody Rol rol) {
        Rol existingRol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));

        existingRol.setNombre(rol.getNombre());
        return ResponseEntity.ok(rolRepository.save(existingRol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }
        rolRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}