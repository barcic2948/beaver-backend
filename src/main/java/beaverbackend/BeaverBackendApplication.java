package beaverbackend;

import beaverbackend.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class BeaverBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeaverBackendApplication.class, args);
	}

}
