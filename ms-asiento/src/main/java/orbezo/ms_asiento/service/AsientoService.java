package orbezo.ms_asiento.service;

import orbezo.ms_asiento.dto.AsientoRequestDTO;
import orbezo.ms_asiento.dto.AsientoResponseDTO;

import java.util.List;

public interface AsientoService {
    List<AsientoResponseDTO> findAll();
    AsientoResponseDTO findById(Long id);
    List<AsientoResponseDTO> findByBusId(Long idBus);
    AsientoResponseDTO create(AsientoRequestDTO requestDTO);
    AsientoResponseDTO update(Long id, AsientoRequestDTO requestDTO);
    void delete(Long id);
}