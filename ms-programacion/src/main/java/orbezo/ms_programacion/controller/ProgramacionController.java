package orbezo.ms_programacion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.ms_programacion.dto.ProgramacionRequestDTO;
import orbezo.ms_programacion.dto.ProgramacionResponseDTO;
import orbezo.ms_programacion.service.ProgramacionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/programaciones")
@RequiredArgsConstructor
public class ProgramacionController {

    private final ProgramacionService programacionService;

    @GetMapping
    public ResponseEntity<List<ProgramacionResponseDTO>> getAll() {
        return ResponseEntity.ok(programacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramacionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(programacionService.findById(id));
    }

    @GetMapping("/bus/{idBus}")
    public ResponseEntity<List<ProgramacionResponseDTO>> getByBusId(@PathVariable Long idBus) {
        return ResponseEntity.ok(programacionService.findByBusId(idBus));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ProgramacionResponseDTO>> getByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(programacionService.findByFecha(fecha));
    }

    @GetMapping("/bus/{idBus}/fecha/{fecha}")
    public ResponseEntity<List<ProgramacionResponseDTO>> getByBusIdAndFecha(
            @PathVariable Long idBus,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(programacionService.findByBusIdAndFecha(idBus, fecha));
    }

    @PostMapping
    public ResponseEntity<ProgramacionResponseDTO> create(@Valid @RequestBody ProgramacionRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(programacionService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramacionResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProgramacionRequestDTO requestDTO) {
        return ResponseEntity.ok(programacionService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        programacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
