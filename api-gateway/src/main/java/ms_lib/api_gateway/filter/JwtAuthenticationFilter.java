package ms_lib.api_gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ms_lib.api_gateway.dto.ValidateTokenDTO;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;

    // ✅ USAR LA MISMA CLAVE QUE EN JwtUtil (usuario-service y auth-service)
    private static final String SECRET_STRING = "miClaveSecretaParaJWT1234567890!@#$%";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    public JwtAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static final List<String> PUBLIC_ROUTES = Arrays.asList(
            "/api/auth/login",
            "/api/usuarios"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();

        System.out.println("🔍 [Gateway] Petición a: " + path);

        // 1. Verificar si es ruta pública
        if (isPublicRoute(path, request.getMethod().name())) {
            System.out.println("✅ [Gateway] Ruta pública: " + path);
            return chain.filter(exchange);
        }

        // 2. Obtener token del header
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ [Gateway] Token no encontrado");
            return onError(exchange, "Token no encontrado o mal formado", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        System.out.println("📌 [Gateway] Token recibido: " + token.substring(0, Math.min(token.length(), 30)) + "...");

        // 3. Validar token DIRECTAMENTE en Gateway
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)  // ← USAR LA MISMA CLAVE
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long userId = claims.get("userId", Long.class);
            String username = claims.getSubject();
            String rol = claims.get("rol", String.class);

            System.out.println("✅ [Gateway] Token válido - Usuario: " + username + " - Rol: " + rol);

            // 4. Agregar headers con información del usuario
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", String.valueOf(userId))
                    .header("X-Username", username)
                    .header("X-User-Rol", rol)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            System.out.println("❌ [Gateway] Token inválido: " + e.getMessage());
            return onError(exchange, "Token inválido o expirado", HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isPublicRoute(String path, String method) {
        if (path.equals("/api/auth/login") && method.equals("POST")) return true;
        if (path.equals("/api/usuarios") && method.equals("POST")) return true;
        return false;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", System.currentTimeMillis());
            error.put("status", status.value());
            error.put("error", status.getReasonPhrase());
            error.put("message", message);

            byte[] bytes = objectMapper.writeValueAsBytes(error);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}