package orbezo.ms_destino.service.impl;

import lombok.RequiredArgsConstructor;
import orbezo.ms_destino.dto.DestinoRequestDTO;
import orbezo.ms_destino.dto.DestinoResponseDTO;
import orbezo.ms_destino.entity.Destino;
import orbezo.ms_destino.exception.ResourceNotFoundException;
import orbezo.ms_destino.repository.DestinoRepository;
import orbezo.ms_destino.service.DestinoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinoServiceImpl implements DestinoService {

    private final DestinoRepository destinoRepository;

    private DestinoResponseDTO toResponseDTO(Destino destino) {
        return new DestinoResponseDTO(
                destino.getIdDestino(),
                destino.getCiuDest(),
                destino.getCostDest()
        );
    }

    private Destino toEntity(DestinoRequestDTO requestDTO) {
        Destino destino = new Destino();
        destino.setCiuDest(requestDTO.getCiuDest());
        destino.setCostDest(requestDTO.getCostDest());
        return destino;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinoResponseDTO> findAll() {
        return destinoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DestinoResponseDTO findById(Long id) {
        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destino no encontrado con ID: " + id));
        return toResponseDTO(destino);
    }

    @Override
    @Transactional
    public DestinoResponseDTO create(DestinoRequestDTO requestDTO) {
        Destino destino = toEntity(requestDTO);
        Destino saved = destinoRepository.save(destino);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public DestinoResponseDTO update(Long id, DestinoRequestDTO requestDTO) {
        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destino no encontrado con ID: " + id));

        destino.setCiuDest(requestDTO.getCiuDest());
        destino.setCostDest(requestDTO.getCostDest());

        return toResponseDTO(destinoRepository.save(destino));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!destinoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destino no encontrado con ID: " + id);
        }
        destinoRepository.deleteById(id);
    }
}