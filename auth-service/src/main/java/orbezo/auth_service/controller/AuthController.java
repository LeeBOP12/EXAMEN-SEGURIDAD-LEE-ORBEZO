package orbezo.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import orbezo.auth_service.dto.LoginRequestDTO;
import orbezo.auth_service.dto.ValidateTokenDTO;
import orbezo.auth_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ ENDPOINT LOGIN AGREGADO
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenDTO> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return ResponseEntity.ok(authService.validateToken(token));
    }
}