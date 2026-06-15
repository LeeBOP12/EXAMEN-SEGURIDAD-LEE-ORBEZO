package orbezo.ms_destino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsDestinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDestinoApplication.class, args);
	}

}
