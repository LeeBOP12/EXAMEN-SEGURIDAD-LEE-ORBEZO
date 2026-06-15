package orbezo.ms_asiento.service.impl;

import lombok.RequiredArgsConstructor;
import orbezo.ms_asiento.dto.AsientoRequestDTO;
import orbezo.ms_asiento.dto.AsientoResponseDTO;
import orbezo.ms_asiento.entity.Asiento;
import orbezo.ms_asiento.repository.AsientoRepository;
import orbezo.ms_asiento.service.AsientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsientoServiceImpl implements AsientoService {

    private final AsientoRepository asientoRepository;

    private AsientoResponseDTO toResponseDTO(Asiento asiento) {
        return new AsientoResponseDTO(
                asiento.getIdAsiento(),
                asiento.getIdBus(),
                asiento.getNumAsi(),
                asiento.getEstAsi()
        );
    }

    private Asiento toEntity(AsientoRequestDTO requestDTO) {
        Asiento asiento = new Asiento();
        asiento.setIdBus(requestDTO.getIdBus());
        asiento.setNumAsi(requestDTO.getNumAsi());
        asiento.setEstAsi(requestDTO.getEstAsi() != null ? requestDTO.getEstAsi() : "D");
        return asiento;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsientoResponseDTO> findAll() {
        return asientoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AsientoResponseDTO findById(Long id) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asiento no encontrado con ID: " + id));
        return toResponseDTO(asiento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsientoResponseDTO> findByBusId(Long idBus) {
        return asientoRepository.findByIdBus(idBus)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AsientoResponseDTO create(AsientoRequestDTO requestDTO) {
        if (asientoRepository.existsByNumAsiAndIdBus(requestDTO.getNumAsi(), requestDTO.getIdBus())) {
            throw new RuntimeException("Ya existe el asiento " + requestDTO.getNumAsi() + " para este bus");
        }
        Asiento asiento = toEntity(requestDTO);
        return toResponseDTO(asientoRepository.save(asiento));
    }

    @Override
    @Transactional
    public AsientoResponseDTO update(Long id, AsientoRequestDTO requestDTO) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asiento no encontrado con ID: " + id));

        asiento.setIdBus(requestDTO.getIdBus());
        asiento.setNumAsi(requestDTO.getNumAsi());
        asiento.setEstAsi(requestDTO.getEstAsi());

        return toResponseDTO(asientoRepository.save(asiento));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!asientoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asiento no encontrado con ID: " + id);
        }
        asientoRepository.deleteById(id);
    }
}
