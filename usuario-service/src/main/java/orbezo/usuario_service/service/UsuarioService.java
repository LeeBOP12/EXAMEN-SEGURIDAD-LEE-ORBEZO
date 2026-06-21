package orbezo.usuario_service.service;

import lombok.RequiredArgsConstructor;
import orbezo.usuario_service.dto.LoginRequestDTO;
import orbezo.usuario_service.dto.UsuarioRequestDTO;
import orbezo.usuario_service.dto.UsuarioResponseDTO;
import orbezo.usuario_service.entity.Rol;
import orbezo.usuario_service.entity.Usuario;
import orbezo.usuario_service.repository.RolRepository;
import orbezo.usuario_service.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return convertToDTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findByUsername(String username) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioResponseDTO dto = convertToDTO(usuario);

        // 🔴 INYECTAMOS password SOLO para auth (hack controlado)
        dto.setPassword(usuario.getPassword());

        return dto;
    }

    @Transactional
    public UsuarioResponseDTO register(UsuarioRequestDTO request) {
        // Validar si el usuario ya existe
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya está registrado");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setEmail(request.getEmail());
        usuario.setActivo(true);

        // Asignar roles
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            List<Rol> roles = request.getRoles().stream()
                    .map(rolNombre -> rolRepository.findByNombreRol(rolNombre)
                            .orElseGet(() -> {
                                Rol nuevoRol = new Rol();
                                nuevoRol.setNombreRol(rolNombre);
                                return rolRepository.save(nuevoRol);
                            }))
                    .collect(Collectors.toList());
            usuario.setRoles(roles);
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToDTO(savedUsuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO validarCredenciales(LoginRequestDTO loginRequest) {
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        return convertToDTO(usuario);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO convertToDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // 🔴 IMPORTANTE
                .email(usuario.getEmail())
                .roles(usuario.getRoles().stream()
                        .map(Rol::getNombreRol)
                        .collect(Collectors.toList()))
                .activo(usuario.getActivo())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }
}