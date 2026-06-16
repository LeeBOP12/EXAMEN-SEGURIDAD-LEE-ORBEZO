package orbezo.ms_programacion.client;

import orbezo.ms_programacion.dto.BusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-bus")
public interface BusFeignClient {

    @GetMapping("/api/buses/{id}")
    BusDTO getBusById(@PathVariable("id") Long id);
}