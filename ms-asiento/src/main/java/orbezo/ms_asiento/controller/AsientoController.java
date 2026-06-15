package orbezo.ms_asiento.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.ms_asiento.dto.AsientoRequestDTO;
import orbezo.ms_asiento.dto.AsientoResponseDTO;
import orbezo.ms_asiento.service.AsientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoService asientoService;

    @GetMapping
    public ResponseEntity<List<AsientoResponseDTO>> getAll() {
        return ResponseEntity.ok(asientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsientoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(asientoService.findById(id));
    }

    @GetMapping("/bus/{idBus}")
    public ResponseEntity<List<AsientoResponseDTO>> getByBusId(@PathVariable Long idBus) {
        return ResponseEntity.ok(asientoService.findByBusId(idBus));
    }

    @PostMapping
    public ResponseEntity<AsientoResponseDTO> create(@Valid @RequestBody AsientoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asientoService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsientoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AsientoRequestDTO requestDTO) {
        return ResponseEntity.ok(asientoService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        asientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}