package org.sid;


import org.sid.entities.Animateur;
import org.sid.entities.Salle;
import org.sid.entities.Ticket;
import org.sid.service.IParcInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class ParcProjetApplication implements CommandLineRunner {
	@Autowired private IParcInitService parcInitService;
	@Autowired private RepositoryRestConfiguration restConfiguration;
	public static void main(String[] args) {
	SpringApplication.run(ParcProjetApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	restConfiguration.exposeIdsFor(Animateur.class, Salle.class, Ticket.class);
	parcInitService.initArrondissements();
	parcInitService.initParcs();
	parcInitService.initSalles();
	parcInitService.initPlaces();
	parcInitService.initSceances();
	parcInitService.initCategories();
	parcInitService.initAnimateurs();
	parcInitService.initProjections();
	parcInitService.initTickets();
	}


}
