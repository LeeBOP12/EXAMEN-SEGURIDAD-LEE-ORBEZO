package orbezo.ms_bus.service.impl;

import lombok.RequiredArgsConstructor;
import orbezo.ms_bus.dto.BusRequestDTO;
import orbezo.ms_bus.dto.BusResponseDTO;
import orbezo.ms_bus.entity.Bus;
import orbezo.ms_bus.repository.BusRepository;
import orbezo.ms_bus.service.BusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    private BusResponseDTO toResponseDTO(Bus bus) {
        return new BusResponseDTO(
                bus.getIdBus(),
                bus.getModBus(),
                bus.getPlacaBus(),
                bus.getCapBus()
        );
    }

    private Bus toEntity(BusRequestDTO requestDTO) {
        Bus bus = new Bus();
        bus.setModBus(requestDTO.getModBus());
        bus.setPlacaBus(requestDTO.getPlacaBus());
        bus.setCapBus(requestDTO.getCapBus());
        return bus;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusResponseDTO> findAll() {
        return busRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BusResponseDTO findById(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus no encontrado con ID: " + id));
        return toResponseDTO(bus);
    }

    @Override
    @Transactional
    public BusResponseDTO create(BusRequestDTO requestDTO) {
        if (busRepository.existsByPlacaBus(requestDTO.getPlacaBus())) {
            throw new RuntimeException("Ya existe un bus con la placa: " + requestDTO.getPlacaBus());
        }
        Bus bus = toEntity(requestDTO);
        Bus saved = busRepository.save(bus);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public BusResponseDTO update(Long id, BusRequestDTO requestDTO) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus no encontrado con ID: " + id));

        bus.setModBus(requestDTO.getModBus());
        bus.setPlacaBus(requestDTO.getPlacaBus());
        bus.setCapBus(requestDTO.getCapBus());

        return toResponseDTO(busRepository.save(bus));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!busRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bus no encontrado con ID: " + id);
        }
        busRepository.deleteById(id);
    }
}