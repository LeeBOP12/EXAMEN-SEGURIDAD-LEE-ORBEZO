package orbezo.ms_reserva.service;

import orbezo.ms_reserva.dto.ReservaRequestDTO;
import orbezo.ms_reserva.dto.ReservaResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    List<ReservaResponseDTO> findAll();
    ReservaResponseDTO findById(Long id);
    List<ReservaResponseDTO> findByClienteId(Long idCliente);
    List<ReservaResponseDTO> findByProgramacionId(Long idProgramacion);
    List<ReservaResponseDTO> findByFecha(LocalDate fechaReser);
    List<ReservaResponseDTO> findByClienteIdAndFechaRange(Long idCliente, LocalDate startDate, LocalDate endDate);
    ReservaResponseDTO create(ReservaRequestDTO requestDTO);
    ReservaResponseDTO update(Long id, ReservaRequestDTO requestDTO);
    void delete(Long id);
}