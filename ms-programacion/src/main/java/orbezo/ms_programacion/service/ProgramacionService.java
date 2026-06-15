package orbezo.ms_programacion.service;

import orbezo.ms_programacion.dto.ProgramacionRequestDTO;
import orbezo.ms_programacion.dto.ProgramacionResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ProgramacionService {
    List<ProgramacionResponseDTO> findAll();
    ProgramacionResponseDTO findById(Long id);
    List<ProgramacionResponseDTO> findByBusId(Long idBus);
    List<ProgramacionResponseDTO> findByFecha(LocalDate fecha);
    List<ProgramacionResponseDTO> findByBusIdAndFecha(Long idBus, LocalDate fecha);
    ProgramacionResponseDTO create(ProgramacionRequestDTO requestDTO);
    ProgramacionResponseDTO update(Long id, ProgramacionRequestDTO requestDTO);
    void delete(Long id);
}
