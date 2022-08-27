package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.factory.IAccountFactory;
import fr.potrunks.gestiondepensebackend.factory.ISpentCategoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class GestionDepenseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDepenseBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(IAccountFactory accountFactory, ISpentCategoryFactory spentCategoryFactory) {
		return args -> {
			log.info("Spent Manager Back API v1.1.0");
			log.warn("Start launch all running method...");

			/*
			accountFactory.AdministratorAccountFabricator("Alexis", "ARRIAL", "potrunks@hotmail.com", "Trunks92!");
			accountFactory.NoAdminAccountFabricator("Valerie", "PAUCHET", "val_chan@hotmail.com", "Trunks92!");

			List<String> spentCategoryNameListToAdd = new ArrayList<>();
			spentCategoryNameListToAdd.add("Remboursement partiel");
			spentCategoryNameListToAdd.add("Catégorie Test");
			spentCategoryNameListToAdd.add("Avance");
			spentCategoryFactory.SpentCategoryFabricator(spentCategoryNameListToAdd);
			*/

			log.warn("End of all running method");
		};
	}
}
