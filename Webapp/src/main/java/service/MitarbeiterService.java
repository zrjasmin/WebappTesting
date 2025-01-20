package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.AnforderungDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import model.Anforderung;
import model.Mitarbeiter.Rolle;

import java.io.Serializable;

@Named
@SessionScoped
public class MitarbeiterService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private dao.MitarbeiterDao arbeiterDao;
	public static List<model.Mitarbeiter> arbeiterListe = new ArrayList<>(); 
			
	private model.Mitarbeiter aktuellerMitarbeiter;	
	private List<String> mitarbeiterString = new ArrayList<>();
	
	
	
	public MitarbeiterService() {
		arbeiterDao = new dao.MitarbeiterDao();
		
		/*model.Mitarbeiter m1 = new model.Mitarbeiter("Markus", "Müller", "h", "h", Rolle.TESTER);
		model.Mitarbeiter m2 = new model.Mitarbeiter("Jasmin", "Zimmer", "h", "h", Rolle.TESTER);
		arbeiterListe.add(m1);
		arbeiterListe.add(m2);*/
		
		arbeiterListe.addAll(addToListe());
		arbeiterDao.saveAllMitarbeiter(arbeiterListe);
		setAktuellerMitarbeiter(arbeiterListe.get(0));
		convertMitarbeiterString();
		
	}
	/*alle Mitarbeiter aus Datenbank auslesen*/
	public List<model.Mitarbeiter> addToListe() {
		return arbeiterDao.alleMitarbeiter();
	}
	/*Mitarbeiter speichern*/
	public void saveMitarbeiter(model.Mitarbeiter mitarbeiter) {
		arbeiterListe.add(mitarbeiter);
		arbeiterDao.saveMitarbeiter(mitarbeiter);
	}
	
	public model.Mitarbeiter getMitarbeiter(int index) {
		return arbeiterDao.alleMitarbeiter().get(index);
	}
	
	public List<model.Mitarbeiter> getArbeiterListe() {
		return arbeiterListe;
	}
	
	/*getter und setter für momentanen Nutzer*/
	public model.Mitarbeiter getAktuellerMitarbeiter() {
		return aktuellerMitarbeiter;
	}
	
	public void setAktuellerMitarbeiter (model.Mitarbeiter aktuellerMitarbeiter) {
		this.aktuellerMitarbeiter = aktuellerMitarbeiter;
	}
	
	public List<String> getMitarbeiterString() {
		return mitarbeiterString;
	}
	
	
	/*Mitarbeiter Daten als String konvertieren*/
	public void convertMitarbeiterString() {
		for(model.Mitarbeiter arbeiter : arbeiterListe) {
			String name = arbeiter.getMitarbeiterVorn() + " " + arbeiter.getMitarbeiterNach();
			mitarbeiterString.add(name);
			
		}
	}
	
	
	
	
	public static void main(String[]args) {
		MitarbeiterService service = new MitarbeiterService();
		
	
	}
	
	
	
	
	
}
