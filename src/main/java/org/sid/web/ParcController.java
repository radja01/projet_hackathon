package org.sid.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.sid.dao.CategorieRepository;
import org.sid.dao.ParcRepository;
import org.sid.dao.AnimateurRepository;
import org.sid.dao.PlaceRepository;
import org.sid.dao.ProjectionRepository;
import org.sid.dao.SalleRepository;
import org.sid.dao.SceanceRepository;
import org.sid.dao.TicketRepository;
import org.sid.dao.ArrondissementRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Parc;
import org.sid.entities.Animateur;
import org.sid.entities.Place;
import org.sid.entities.Projection;
import org.sid.entities.Salle;
import org.sid.entities.Sceance;
import org.sid.entities.Ticket;
import org.sid.entities.Arrondissement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ParcController {
	@Autowired
	private AnimateurRepository animateurRepository;
	@Autowired
	private ArrondissementRepository arrondissementRepository;
	@Autowired
	private ParcRepository parcRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private SceanceRepository sceanceRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private TicketRepository ticketRepository;
	

	@GetMapping("/dashboard")
	public String dashboard() {
		
		return "dashboard";
	}
	
	

	@GetMapping("/animateurManagement")
	public String animateurManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Animateur> pageAnimateurs= animateurRepository.findByTitreContains(mc, PageRequest.of(page,5));
		model.addAttribute("listAnimateurs", pageAnimateurs.getContent());
		model.addAttribute("pages", new int[pageAnimateurs.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "animateurManagement";
		 
	}

	@GetMapping("/deleteAnimateur")
	public String deleteAnimateur(Long id, int page, String motCle){
		animateurRepository.deleteById(id);
		return "redirect:/animateurManagement?page="+page+"&motCle="+motCle;
	
	}
	
	@GetMapping("/ticketManagement")
	public String ticketManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Ticket> pageTickets= ticketRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listTickets", pageTickets.getContent());
		model.addAttribute("pages", new int[pageTickets.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "ticketManagement";
		
	}
	
	@GetMapping("/editTicket")
	public String editTicket(Long id, Model model){
		Ticket ticket = ticketRepository.findById(id).get();
		List<Place> Places= placeRepository.findAll();
		List<Projection> Projections= projectionRepository.findAll();
		model.addAttribute("listPlaces", Places);
		model.addAttribute("listProjections", Projections);
		model.addAttribute("ticket", ticket);
		return "editTicketForm";
	}
	
	@GetMapping("/deleteTicket")
	public String deleteTicket(Long id, int page, String motCle){
		ticketRepository.deleteById(id);
		return "redirect:/ticketManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formTicket")
	public String formTicket(Model model){
		List<Place> Places= placeRepository.findAll();
		List<Projection> Projections= projectionRepository.findAll();
		model.addAttribute("listPlaces", Places);
		model.addAttribute("listProjections", Projections);
		model.addAttribute("ticket", new Ticket());
		
		return "formTicket";
	}
	
	@PostMapping("/saveTicket")
	public String saveTicket(Model model, @Valid Ticket ticket, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formTicket";
		ticketRepository.save(ticket);
		return "redirect:/ticketManagement";
	}



	@GetMapping("/parcManagement")
	public String parcManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Parc> pageParcs= parcRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listParcs", pageParcs.getContent());
		model.addAttribute("pages", new int[pageParcs.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "parcManagement";
		
	}
	
	@GetMapping("/editParc")
	public String editParc(Long id, Model model){
		Parc parc = parcRepository.findById(id).get();
		List<Arrondissement> Arrondissements= arrondissementRepository.findAll();
		model.addAttribute("listArrondissements", Arrondissements);
		model.addAttribute("parc", parc);
		return "editParcForm";
	}
	
	@GetMapping("/deleteParc")
	public String deleteParc(Long id, int page, String motCle){
		parcRepository.deleteById(id);
		return "redirect:/parcManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formParc")
	public String formParc(Model model){
		List<Arrondissement> Arrondissements= arrondissementRepository.findAll();
		model.addAttribute("listArrondissements", Arrondissements);
		model.addAttribute("parc", new Parc());
		
		return "formParc";
	}
	
	@PostMapping("/saveParc")
	public String saveParc(Model model, @Valid Parc parc, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formParc";
		parcRepository.save(parc);
		return "redirect:/parcManagement";
	}



	@GetMapping("/placeManagement")
	public String placeManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Place> pagePlaces= placeRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listPlaces", pagePlaces.getContent());
		model.addAttribute("pages", new int[pagePlaces.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "placeManagement";
		
	}
	
	@GetMapping("/editPlace")
	public String editPlace(Long id, Model model){
		Place place = placeRepository.findById(id).get();
		List<Salle> Salles= salleRepository.findAll();
		model.addAttribute("listSalles", Salles);
		model.addAttribute("place", place);
		return "editPlaceForm";
	}
	
	@GetMapping("/deletePlace")
	public String deletePlace(Long id, int page, String motCle){
		placeRepository.deleteById(id);
		return "redirect:/placeManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formPlace")
	public String formPlace(Model model){
		List<Salle> Salles= salleRepository.findAll();
		model.addAttribute("listSalles", Salles);
		model.addAttribute("place", new Place());
		
		return "formPlace";
	}
	
	@PostMapping("/savePlace")
	public String savePlace(Model model, @Valid Place place, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formPlace";
		placeRepository.save(place);
		return "redirect:/placeManagement";
	}
	@GetMapping("/seanceManagement")
	public String seanceManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Sceance> pageSceances= sceanceRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listSceances", pageSceances.getContent());
		model.addAttribute("pages", new int[ pageSceances.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "seanceManagement";
		
	}
	

	
	
	@GetMapping("/projectionManagement")
	public String projectionManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Projection> pageProjections= projectionRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listProjections", pageProjections.getContent());
		model.addAttribute("pages", new int[ pageProjections.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "projectionManagement";
		
	}
	@GetMapping("/editProjection")
	public String editProjection(Long id, Model model){
		Projection projection = projectionRepository.findById(id).get();
		List<Animateur> animateur= animateurRepository.findAll();
		List<Salle> salle= salleRepository.findAll();
		List<Sceance> sceance= sceanceRepository.findAll();
		model.addAttribute("listAnimateurs", animateur);
		model.addAttribute("listSalles", salle);
		model.addAttribute("listSceances", sceance);
		model.addAttribute("projection", projection);
		return "editProjectionForm";
	}
	
	@GetMapping("/deleteProjection")
	public String deleteProjection(Long id, int page, String motCle){
		projectionRepository.deleteById(id);
		return "redirect:/projectionManagement?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formProjection")
	public String formProjectio(Model model){
		List<Animateur> animateur= animateurRepository.findAll();
		List<Salle> salle= salleRepository.findAll();
		List<Sceance> sceance= sceanceRepository.findAll();
		model.addAttribute("projection", new Projection());
		model.addAttribute("listAnimateurs", animateur);
		model.addAttribute("listSalles", salle);
		model.addAttribute("listSceances", sceance);
		return "formProjection";
	}
	@PostMapping("/saveEditProjection")
	public String saveEditProjection(Model model, @Valid Projection projection, BindingResult bindingResult) throws ParseException{
		if(bindingResult.hasErrors()) return "editProjectionForm";
		
		@SuppressWarnings("deprecation")
		Date parsed = new Date(Integer.parseInt(projection.getSdateProjection().split("-")[0])-1900,Integer.parseInt(projection.getSdateProjection().split("-")[1])
				,Integer.parseInt(projection.getSdateProjection().split("-")[2]));

		projection.setDateProjection(parsed);
		projectionRepository.save(projection);
		return "redirect:/projectionManagement";
	}

	
	@PostMapping("/saveProjection")
	public String saveProjection(Model model, @Valid Projection projection, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formProjection";
		projectionRepository.save(projection);
		return "redirect:/projectionManagement";
	}
	
	@GetMapping("/arrondissementManagement")
	public String arrondissementManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Arrondissement> pageArrondissements= arrondissementRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listArrondissements", pageArrondissements.getContent());
		model.addAttribute("pages", new int[pageArrondissements.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "arrondissementManagement";
		
	}
	@GetMapping("/editArrondissement")
	public String editArrondissement(Long id, Model model){
		Arrondissement arrondissement = arrondissementRepository.findById(id).get();
		model.addAttribute("arrondissement", arrondissement);
		return "editArrondissementForm";
	}
	
	@GetMapping("/deleteArrondissement")
	public String deleteArrondissement(Long id, int page, String motCle){
		arrondissementRepository.deleteById(id);
		return "redirect:/arrondissementManagement?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formArrondissement")
	public String formArrondissement(Model model){
		model.addAttribute("arrondissement", new Arrondissement());
		return "formArrondissement";
	}
	
	@PostMapping("/saveArrondissement")
	public String saveArrondissement(Model model, @Valid Arrondissement arrondissement, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formArrondissement";
		arrondissementRepository.save(arrondissement);
		return "redirect:/arrondissementManagement";
	}
	@PostMapping("/saveEditArrondissement")
	public String saveEditArrondissement(Model model, @Valid Arrondissement arrondissement, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "editArrondissementForm";
		arrondissementRepository.save(arrondissement);
		return "redirect:/arrondissementManagement";
	}

	@GetMapping("/categorieManagement")
	public String categorieManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Categorie> pageCategories= categorieRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listCats", pageCategories.getContent());
		model.addAttribute("pages", new int[pageCategories.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "categorieManagement";
		
	}
	@GetMapping("/editCategorie")
	public String editCategorie(Long id, Model model){
		Categorie categorie = categorieRepository.findById(id).get();
		model.addAttribute("categorie", categorie);
		return "editCategorieForm";
	}
	
	@GetMapping("/deleteCategorie")
	public String deleteCategorie(Long id, int page, String motCle){
		categorieRepository.deleteById(id);
		return "redirect:/categorieManagement?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formCategorie")
	public String formCategorie(Model model){
		model.addAttribute("categorie", new Categorie());
		return "formCategorie";
	}
	
	@PostMapping("/saveCategorie")
	public String saveCategorie(Model model, @Valid Categorie categorie, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formArrondissement";
		categorieRepository.save(categorie);
		return "redirect:/categorieManagement";
	}

	

	@GetMapping("/salleManagement")
	public String salleManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Salle> pageSalles= salleRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listSalles", pageSalles.getContent());
		model.addAttribute("pages", new int[pageSalles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "salleManagement";
		
	}
	
	@GetMapping("/editSalle")
	public String editSalle(Long id, Model model){
		Salle salle = salleRepository.findById(id).get();
		List<Parc> Parc= parcRepository.findAll();
		model.addAttribute("listParcs", Parc);
		model.addAttribute("salle", salle);
		return "editSalleForm";
	}
	
	@GetMapping("/deleteSalle")
	public String deleteSalle(Long id, int page, String motCle){
		salleRepository.deleteById(id);
		return "redirect:/salleManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formSalle")
	public String formSalle(Model model){
		List<Parc> Parcs= parcRepository.findAll();
		model.addAttribute("listParcs", Parcs);
		model.addAttribute("salle", new Salle());
		
		return "formSalle";
	}
	
	@PostMapping("/saveSalle")
	public String saveSalle(Model model, @Valid Salle salle, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formSalle";
		salleRepository.save(salle);
		return "redirect:/salleManagement";
	}

	@GetMapping(path = "/")
	public String Home() {
			return "redirect:/dashboard";

	}

}
