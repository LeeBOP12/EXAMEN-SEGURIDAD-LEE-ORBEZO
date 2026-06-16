package orbezo.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenDTO {
    private boolean valid;
    private Long userId;
    private String username;
    private String rol;
}