package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.factory.IMainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class GestionDepenseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDepenseBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(IMainFactory mainFactory) {
		return args -> {
			log.warn("Start launch all running method...");
			mainFactory.AdministratorAccountFabricator("Alexis", "ARRIAL", "potrunks@hotmail.com", "Trunks92!");
			mainFactory.SpentCategoryFabricator();
			log.warn("End of all running method");
		};
	}
}
