package Api_de_zoologico.zoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ZooApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooApplication.class, args);
	}

}
