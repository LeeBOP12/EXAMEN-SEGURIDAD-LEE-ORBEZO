package orbezo.ms_cliente.service.impl;

import lombok.RequiredArgsConstructor;
import orbezo.ms_cliente.dto.ClienteRequestDTO;
import orbezo.ms_cliente.dto.ClienteResponseDTO;
import orbezo.ms_cliente.entity.Cliente;
import orbezo.ms_cliente.exception.ResourceNotFoundException;
import orbezo.ms_cliente.repository.ClienteRepository;
import orbezo.ms_cliente.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getNomCli(),
                cliente.getApeCli(),
                cliente.getEdadCli(),
                cliente.getSexoCli()
        );
    }

    private Cliente toEntity(ClienteRequestDTO requestDTO) {
        Cliente cliente = new Cliente();
        cliente.setNomCli(requestDTO.getNomCli());
        cliente.setApeCli(requestDTO.getApeCli());
        cliente.setEdadCli(requestDTO.getEdadCli());
        cliente.setSexoCli(requestDTO.getSexoCli());
        return cliente;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        return toResponseDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO requestDTO) {
        Cliente cliente = toEntity(requestDTO);
        Cliente saved = clienteRepository.save(cliente);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));

        cliente.setNomCli(requestDTO.getNomCli());
        cliente.setApeCli(requestDTO.getApeCli());
        cliente.setEdadCli(requestDTO.getEdadCli());
        cliente.setSexoCli(requestDTO.getSexoCli());

        return toResponseDTO(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
