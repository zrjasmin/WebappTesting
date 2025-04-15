package service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ArbeiterService implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private dao.ArbeiterDao dao;
	
	
	private model.Arbeiter aktuellerMitarbeiter;

	public static List<model.Arbeiter> arbeiterListe = new ArrayList<>();
	
	
	@PostConstruct
	 public void init() {
		try {
		
		} catch(Exception e ) {
			
		}
		
	}
	
	
	public ArbeiterService() {
		dao = new dao.ArbeiterDao();
		arbeiterListe.clear();

		
		arbeiterListe.addAll(getArbeiterListe());
		aktuellerMitarbeiter = new model.Arbeiter();

	}
	
	
	public List<model.Arbeiter> getArbeiterListe() {
		return arbeiterListe;
	}

	
	public model.Arbeiter getAktuellerMitarbeiter() {
		return aktuellerMitarbeiter;
	}
	
	public void setAktuellerMitarbeiter(model.Arbeiter arbeiter) {
		this.aktuellerMitarbeiter = arbeiter;
		System.out.println("geänderter Mitarbeiter:" + arbeiter.getArbeiterId());
	}
	
	
	//Berechtigungen managen
	
	
	
	
}
	


