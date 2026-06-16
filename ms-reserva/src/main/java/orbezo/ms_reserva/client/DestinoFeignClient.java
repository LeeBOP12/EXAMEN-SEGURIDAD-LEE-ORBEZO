package orbezo.ms_reserva.client;

import orbezo.ms_reserva.dto.DestinoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-destino")
public interface DestinoFeignClient {

    @GetMapping("/api/destinos/{id}")
    DestinoDTO getDestinoById(@PathVariable("id") Long id);
}