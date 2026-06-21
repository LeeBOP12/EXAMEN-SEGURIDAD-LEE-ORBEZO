package orbezo.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.auth_service.dto.LoginRequestDTO;
import orbezo.auth_service.dto.LoginResponseDTO;
import orbezo.auth_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}