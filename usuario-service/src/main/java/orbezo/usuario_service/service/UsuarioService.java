package orbezo.usuario_service.service;

import lombok.RequiredArgsConstructor;
import orbezo.usuario_service.dto.LoginRequestDTO;
import orbezo.usuario_service.dto.UsuarioRequestDTO;
import orbezo.usuario_service.dto.UsuarioResponseDTO;
import orbezo.usuario_service.entity.Rol;
import orbezo.usuario_service.entity.Usuario;
import orbezo.usuario_service.repository.RolRepository;
import orbezo.usuario_service.repository.UsuarioRepository;
import orbezo.usuario_service.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ===== Conversiones =====
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setRoles(usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet()));
        dto.setActivo(usuario.getActivo());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setPassword(usuario.getPassword());  // ✅ AGREGAR ESTA LÍNEA
        return dto;
    }

    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setEmail(dto.getEmail());
        usuario.setRoles(dto.getRoles().stream()
                .map(nombre -> rolRepository.findByNombre(nombre)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no existe: " + nombre)))
                .collect(Collectors.toSet()));
        return usuario;
    }

    // ===== CRUD Usuarios =====
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        return usuarioRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findByUsername(String username) {
        return usuarioRepository.findByUsernameWithRoles(username)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username ya existe");
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ya existe");
        }
        return toResponseDTO(usuarioRepository.save(toEntity(dto)));
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setRoles(dto.getRoles().stream()
                .map(nombre -> rolRepository.findByNombre(nombre)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no existe: " + nombre)))
                .collect(Collectors.toSet()));

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // ===== Auth =====
    public Map<String, Object> login(LoginRequestDTO loginRequest) {
        Usuario usuario = usuarioRepository.findByUsernameWithRoles(loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        String rol = usuario.getRoles().stream()
                .findFirst()
                .map(Rol::getNombre)
                .orElse("USER");

        String token = jwtUtil.generateToken(usuario.getIdUsuario(), usuario.getUsername(), rol);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", usuario.getUsername());
        response.put("roles", usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet()));
        response.put("userId", usuario.getIdUsuario());
        response.put("expiresIn", jwtUtil.getExpiration());

        return response;
    }
}
