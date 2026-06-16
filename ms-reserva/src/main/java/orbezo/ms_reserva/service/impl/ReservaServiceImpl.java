package orbezo.ms_reserva.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import orbezo.ms_reserva.client.ClienteFeignClient;
import orbezo.ms_reserva.client.DestinoFeignClient;
import orbezo.ms_reserva.client.ProgramacionFeignClient;
import orbezo.ms_reserva.dto.ReservaRequestDTO;
import orbezo.ms_reserva.dto.ReservaResponseDTO;
import orbezo.ms_reserva.entity.Reserva;
import orbezo.ms_reserva.exception.ResourceNotFoundException;
import orbezo.ms_reserva.repository.ReservaRepository;
import orbezo.ms_reserva.service.ReservaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteFeignClient clienteFeignClient;
    private final ProgramacionFeignClient programacionFeignClient;
    private final DestinoFeignClient destinoFeignClient;

    private ReservaResponseDTO toResponseDTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getIdReserva(),
                reserva.getFechaReser(),
                reserva.getHoraReser(),
                reserva.getIdCliente(),
                reserva.getIdProgramacion(),
                reserva.getIdDestino()
        );
    }

    private Reserva toEntity(ReservaRequestDTO requestDTO) {
        Reserva reserva = new Reserva();
        reserva.setFechaReser(requestDTO.getFechaReser());
        reserva.setHoraReser(requestDTO.getHoraReser());
        reserva.setIdCliente(requestDTO.getIdCliente());
        reserva.setIdProgramacion(requestDTO.getIdProgramacion());
        reserva.setIdDestino(requestDTO.getIdDestino());
        return reserva;
    }

    // Método privado para validar existencia con Feign
    private void validateRelations(ReservaRequestDTO requestDTO) {
        try {
            // Validar que el cliente existe
            clienteFeignClient.getClienteById(requestDTO.getIdCliente());

            // Validar que la programación existe
            programacionFeignClient.getProgramacionById(requestDTO.getIdProgramacion());

            // Validar que el destino existe
            destinoFeignClient.getDestinoById(requestDTO.getIdDestino());

        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Uno de los recursos relacionados no existe");
        } catch (FeignException e) {
            throw new RuntimeException("Error al validar relaciones: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponseDTO findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        return toResponseDTO(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findByClienteId(Long idCliente) {
        return reservaRepository.findByIdCliente(idCliente)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findByProgramacionId(Long idProgramacion) {
        return reservaRepository.findByIdProgramacion(idProgramacion)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findByFecha(LocalDate fechaReser) {
        return reservaRepository.findByFechaReser(fechaReser)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findByClienteIdAndFechaRange(Long idCliente, LocalDate startDate, LocalDate endDate) {
        return reservaRepository.findByIdClienteAndFechaReserBetween(idCliente, startDate, endDate)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservaResponseDTO create(ReservaRequestDTO requestDTO) {
        // Validar que todas las relaciones existen usando Feign
        validateRelations(requestDTO);

        Reserva reserva = toEntity(requestDTO);
        return toResponseDTO(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public ReservaResponseDTO update(Long id, ReservaRequestDTO requestDTO) {
        // Validar que todas las relaciones existen usando Feign
        validateRelations(requestDTO);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        reserva.setFechaReser(requestDTO.getFechaReser());
        reserva.setHoraReser(requestDTO.getHoraReser());
        reserva.setIdCliente(requestDTO.getIdCliente());
        reserva.setIdProgramacion(requestDTO.getIdProgramacion());
        reserva.setIdDestino(requestDTO.getIdDestino());

        return toResponseDTO(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
        reservaRepository.deleteById(id);
    }
}