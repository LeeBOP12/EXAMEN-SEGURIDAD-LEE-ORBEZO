package orbezo.ms_cliente.service;

import orbezo.ms_cliente.dto.ClienteRequestDTO;
import orbezo.ms_cliente.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteResponseDTO> findAll();
    ClienteResponseDTO findById(Long id);
    ClienteResponseDTO create(ClienteRequestDTO requestDTO);
    ClienteResponseDTO update(Long id, ClienteRequestDTO requestDTO);
    void delete(Long id);
}