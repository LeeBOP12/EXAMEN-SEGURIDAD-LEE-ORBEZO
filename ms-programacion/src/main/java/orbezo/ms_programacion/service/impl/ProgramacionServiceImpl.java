package orbezo.ms_programacion.service.impl;

import lombok.RequiredArgsConstructor;
import orbezo.ms_programacion.dto.ProgramacionRequestDTO;
import orbezo.ms_programacion.dto.ProgramacionResponseDTO;
import orbezo.ms_programacion.entity.Programacion;
import orbezo.ms_programacion.repository.ProgramacionRepository;
import orbezo.ms_programacion.service.ProgramacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramacionServiceImpl implements ProgramacionService {

    private final ProgramacionRepository programacionRepository;

    private ProgramacionResponseDTO toResponseDTO(Programacion programacion) {
        return new ProgramacionResponseDTO(
                programacion.getIdProgramacion(),
                programacion.getIdBus(),
                programacion.getFecha(),
                programacion.getHora()
        );
    }

    private Programacion toEntity(ProgramacionRequestDTO requestDTO) {
        Programacion programacion = new Programacion();
        programacion.setIdBus(requestDTO.getIdBus());
        programacion.setFecha(requestDTO.getFecha());
        programacion.setHora(requestDTO.getHora());
        return programacion;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramacionResponseDTO> findAll() {
        return programacionRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramacionResponseDTO findById(Long id) {
        Programacion programacion = programacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programación no encontrada con ID: " + id));
        return toResponseDTO(programacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramacionResponseDTO> findByBusId(Long idBus) {
        return programacionRepository.findByIdBus(idBus)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramacionResponseDTO> findByFecha(LocalDate fecha) {
        return programacionRepository.findByFecha(fecha)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramacionResponseDTO> findByBusIdAndFecha(Long idBus, LocalDate fecha) {
        return programacionRepository.findByIdBusAndFecha(idBus, fecha)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProgramacionResponseDTO create(ProgramacionRequestDTO requestDTO) {
        if (programacionRepository.existsByIdBusAndFechaAndHora(
                requestDTO.getIdBus(),
                requestDTO.getFecha(),
                requestDTO.getHora())) {
            throw new RuntimeException("Ya existe una programación para este bus en esa fecha y hora");
        }
        Programacion programacion = toEntity(requestDTO);
        return toResponseDTO(programacionRepository.save(programacion));
    }

    @Override
    @Transactional
    public ProgramacionResponseDTO update(Long id, ProgramacionRequestDTO requestDTO) {
        Programacion programacion = programacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programación no encontrada con ID: " + id));

        programacion.setIdBus(requestDTO.getIdBus());
        programacion.setFecha(requestDTO.getFecha());
        programacion.setHora(requestDTO.getHora());

        return toResponseDTO(programacionRepository.save(programacion));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!programacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Programación no encontrada con ID: " + id);
        }
        programacionRepository.deleteById(id);
    }
}