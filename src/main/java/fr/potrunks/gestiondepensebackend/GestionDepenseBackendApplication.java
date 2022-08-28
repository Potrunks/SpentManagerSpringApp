package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.factory.IAccountFactory;
import fr.potrunks.gestiondepensebackend.factory.ISpentCategoryFactory;
import fr.potrunks.gestiondepensebackend.factory.ISpentFactory;
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
	CommandLineRunner run(IAccountFactory accountFactory, ISpentCategoryFactory spentCategoryFactory, ISpentFactory spentFactory) {
		return args -> {
			log.info("Spent Manager Back API v1.1.2");
			log.warn("Start launch all running method...");

			List<Long> idSpentList = new ArrayList<>();
			idSpentList.add(1021L);
			idSpentList.add(1031L);
			spentFactory.spentDestructor(idSpentList);

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
