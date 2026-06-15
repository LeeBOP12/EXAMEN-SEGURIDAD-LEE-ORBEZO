package orbezo.ms_bus.service;

import orbezo.ms_bus.dto.BusRequestDTO;
import orbezo.ms_bus.dto.BusResponseDTO;

import java.util.List;

public interface BusService {
    List<BusResponseDTO> findAll();
    BusResponseDTO findById(Long id);
    BusResponseDTO create(BusRequestDTO requestDTO);
    BusResponseDTO update(Long id, BusRequestDTO requestDTO);
    void delete(Long id);
}
