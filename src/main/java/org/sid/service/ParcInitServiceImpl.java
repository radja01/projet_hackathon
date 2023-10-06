package org.sid.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

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
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ParcInitServiceImpl implements IParcInitService {
	
	@Autowired
	private ArrondissementRepository arrondissementRepository;
	@Autowired 
	private ParcRepository parcRepository;
	@Autowired 
	private SalleRepository salleRepository;
	@Autowired 
	private PlaceRepository placeRepository;
	@Autowired 
	private SceanceRepository sceanceRepository;
	@Autowired 
	private AnimateurRepository animateurRepository;
	@Autowired 
	private ProjectionRepository projectionRepository;
	@Autowired 
	private CategorieRepository categorieRepository;
	@Autowired 
	private TicketRepository ticketRepository;
	
	@Override
	public void initArrondissements() {
		Stream.of("Casablanca","Marrakech","Essaouira","Rabat","Tanger").forEach(nameArrondissement->{
			Arrondissement arrondissement=new Arrondissement();
			arrondissement.setName(nameArrondissement);
			arrondissementRepository.save(arrondissement);
		});
		
	}

	@Override
	public void initParcs() {
		arrondissementRepository.findAll().forEach(v->{
			Stream.of("Megarama","Imax","HBO","Netflix").forEach(nameParc->{
				Parc parc = new Parc();
				parc.setName(nameParc);
				parc.setNombreSalles(3+(int)(Math.random()*2));
				parc.setArrondissement(v);
				parcRepository.save(parc);
			});
		});
		
	}

	@Override
	public void initSalles() {
		parcRepository.findAll().forEach(parc->{
			for(int i=0;i<parc.getNombreSalles();i++) {
			Salle salle=new Salle();
			salle.setName("Salle "+(i+1));
			salle.setParc(parc);
			salle.setNombrePlace(15+(int)(Math.random()*2));
			salleRepository.save(salle);
			}});		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlace();i++) {
			Place place=new Place();
			place.setNumero(i+1);
			place.setSalle(salle);
			placeRepository.save(place);
			}
			});		
	}

	@Override
	public void initSceances() {
		DateFormat dateFormat=new SimpleDateFormat("HH:mm");
		Stream.of("14:00","15:00","19:00","21:00").forEach(s->{
		Sceance sceance=new Sceance();
		try {
		sceance.setHeureDebut(dateFormat.parse(s));
		sceanceRepository.save(sceance);
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		});		
	}

	@Override
	public void initCategories() {
		Stream.of("Histoire","Actions","Fiction","Drama").forEach(cat->{
			Categorie categorie=new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
			});
		
	}

	@Override
	public void initAnimateurs() {
		double[] durees=new double[] {1.0,1.5,2.5,1.4};
		List<Categorie> categories=categorieRepository.findAll();
		Stream.of("Game Of Thrones","Lucifer","Dracula","Spider Man","Iron Man").forEach(titreAnimateur->{
		Animateur animateur=new Animateur();
		animateur.setTitre(titreAnimateur);
		animateur.setDuree(durees[new Random().nextInt(durees.length)]);
		animateur.setPhoto(titreAnimateur.replaceAll(" ", "")+".jpg");
		animateur.setCategorie(categories.get(new Random().nextInt(categories.size())));
		animateurRepository.save(animateur);
		});
		
	}

	@Override
	public void initProjections() {
		double[] prices=new double[] {30,50,60,70,90,100};
		List<Animateur> animateurs=animateurRepository.findAll();
		arrondissementRepository.findAll().forEach(arrondissement->{
				arrondissement.getParcs().forEach(parc->{
						parc.getSalles().forEach(salle->{
							int index= new Random().nextInt(animateurs.size());
							Animateur animateur = animateurs.get(index);
								sceanceRepository.findAll().forEach(sceance->{
									Projection projection=new Projection();
									projection.setDateProjection(new Date());
									projection.setAnimateur(animateur);
									projection.setPrix(prices[new Random().nextInt(prices.length)]);
									projection.setSalle(salle);
									projection.setSceance(sceance);
									projectionRepository.save(projection);
									
							});
						});
				});
		});
		
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(p->{
			p.getSalle().getPlaces().forEach(place->{
				Ticket ticket =  new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(p.getPrix());
				ticket.setProjection(p);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});
	}
	


}
