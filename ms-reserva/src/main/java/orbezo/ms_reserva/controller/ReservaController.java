package orbezo.ms_reserva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.ms_reserva.dto.ReservaRequestDTO;
import orbezo.ms_reserva.dto.ReservaResponseDTO;
import orbezo.ms_reserva.service.ReservaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> getAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ReservaResponseDTO>> getByClienteId(@PathVariable Long idCliente) {
        return ResponseEntity.ok(reservaService.findByClienteId(idCliente));
    }

    @GetMapping("/programacion/{idProgramacion}")
    public ResponseEntity<List<ReservaResponseDTO>> getByProgramacionId(@PathVariable Long idProgramacion) {
        return ResponseEntity.ok(reservaService.findByProgramacionId(idProgramacion));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ReservaResponseDTO>> getByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(reservaService.findByFecha(fecha));
    }

    @GetMapping("/cliente/{idCliente}/fechas")
    public ResponseEntity<List<ReservaResponseDTO>> getByClienteIdAndFechaRange(
            @PathVariable Long idCliente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reservaService.findByClienteIdAndFechaRange(idCliente, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> create(@Valid @RequestBody ReservaRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDTO requestDTO) {
        return ResponseEntity.ok(reservaService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}