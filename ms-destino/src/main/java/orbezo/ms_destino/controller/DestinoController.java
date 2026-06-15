package orbezo.ms_destino.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.ms_destino.dto.DestinoRequestDTO;
import orbezo.ms_destino.dto.DestinoResponseDTO;
import orbezo.ms_destino.service.DestinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
@RequiredArgsConstructor
public class DestinoController {

    private final DestinoService destinoService;

    @GetMapping
    public ResponseEntity<List<DestinoResponseDTO>> getAll() {
        return ResponseEntity.ok(destinoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(destinoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DestinoResponseDTO> create(@Valid @RequestBody DestinoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(destinoService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DestinoRequestDTO requestDTO) {
        return ResponseEntity.ok(destinoService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
