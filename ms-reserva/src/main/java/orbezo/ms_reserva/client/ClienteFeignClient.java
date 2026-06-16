package orbezo.ms_reserva.client;

import orbezo.ms_reserva.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente")
public interface ClienteFeignClient {

    @GetMapping("/api/clientes/{id}")
    ClienteDTO getClienteById(@PathVariable("id") Long id);
}