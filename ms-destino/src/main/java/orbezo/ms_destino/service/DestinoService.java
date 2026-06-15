package orbezo.ms_destino.service;

import orbezo.ms_destino.dto.DestinoRequestDTO;
import orbezo.ms_destino.dto.DestinoResponseDTO;

import java.util.List;

public interface DestinoService {
    List<DestinoResponseDTO> findAll();
    DestinoResponseDTO findById(Long id);
    DestinoResponseDTO create(DestinoRequestDTO requestDTO);
    DestinoResponseDTO update(Long id, DestinoRequestDTO requestDTO);
    void delete(Long id);
}