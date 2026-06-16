package orbezo.ms_reserva.client;

import orbezo.ms_reserva.dto.ProgramacionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-programacion")
public interface ProgramacionFeignClient {

    @GetMapping("/api/programaciones/{id}")
    ProgramacionDTO getProgramacionById(@PathVariable("id") Long id);
}