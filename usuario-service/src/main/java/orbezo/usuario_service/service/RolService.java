package orbezo.usuario_service.service;

import lombok.RequiredArgsConstructor;
import orbezo.usuario_service.dto.RolRequestDTO;
import orbezo.usuario_service.dto.RolResponseDTO;
import orbezo.usuario_service.entity.Rol;
import orbezo.usuario_service.repository.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<RolResponseDTO> findAll() {
        return rolRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RolResponseDTO findById(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
        return convertToDTO(rol);
    }

    @Transactional(readOnly = true)
    public RolResponseDTO findByNombre(String nombre) {
        Rol rol = rolRepository.findByNombreRol(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre));
        return convertToDTO(rol);
    }

    @Transactional
    public RolResponseDTO create(RolRequestDTO request) {
        // Verificar si el rol ya existe
        if (rolRepository.findByNombreRol(request.getNombreRol()).isPresent()) {
            throw new RuntimeException("El rol '" + request.getNombreRol() + "' ya existe");
        }

        Rol rol = new Rol();
        rol.setNombreRol(request.getNombreRol().toUpperCase());

        Rol savedRol = rolRepository.save(rol);
        return convertToDTO(savedRol);
    }

    @Transactional
    public RolResponseDTO update(Long id, RolRequestDTO request) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        // Verificar si el nuevo nombre ya existe (excepto el mismo rol)
        rolRepository.findByNombreRol(request.getNombreRol())
                .ifPresent(existingRol -> {
                    if (!existingRol.getIdRol().equals(id)) {
                        throw new RuntimeException("El rol '" + request.getNombreRol() + "' ya existe");
                    }
                });

        rol.setNombreRol(request.getNombreRol().toUpperCase());
        Rol updatedRol = rolRepository.save(rol);
        return convertToDTO(updatedRol);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
    }

    private RolResponseDTO convertToDTO(Rol rol) {
        return RolResponseDTO.builder()
                .idRol(rol.getIdRol())
                .nombreRol(rol.getNombreRol())
                .build();
    }
}