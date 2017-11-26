package br.com.avenuecode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			System.out.println("CommandLineRunner running products...");
		};
	}
}
