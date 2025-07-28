package com.studioAlanGodoy.partiturasEServicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.studioAlanGodoy.partiturasEServicos.infastructure.security.Client")

public class PartiturasEServicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartiturasEServicosApplication.class, args);
	}

}
