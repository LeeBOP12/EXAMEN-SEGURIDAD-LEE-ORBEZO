package orbezo.ms_bus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.ms_bus.dto.BusRequestDTO;
import orbezo.ms_bus.dto.BusResponseDTO;
import orbezo.ms_bus.service.BusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @GetMapping
    public ResponseEntity<List<BusResponseDTO>> getAll() {
        return ResponseEntity.ok(busService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(busService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BusResponseDTO> create(@Valid @RequestBody BusRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BusRequestDTO requestDTO) {
        return ResponseEntity.ok(busService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        busService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
