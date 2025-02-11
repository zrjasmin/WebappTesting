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
@ViewScoped
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
		arbeiterListe.clear();
		/*
		model.Mitarbeiter m1 = new model.Mitarbeiter("fsd", "dsf", "f", "df", Rolle.RE);
		model.Mitarbeiter m2 = new model.Mitarbeiter("fsd", "dsf", "f", "df", Rolle.RE);
		model.Mitarbeiter m3 = new model.Mitarbeiter("fsd", "dsf", "f", "df", Rolle.RE);
		model.Mitarbeiter m4 = new model.Mitarbeiter("fsd", "dsf", "f", "df", Rolle.RE);
		saveMitarbeiter(m1);
		saveMitarbeiter(m2);
		saveMitarbeiter(m3);
		saveMitarbeiter(m4);
		*/
		arbeiterListe.addAll(addToListe());
		aktuellerMitarbeiter = arbeiterListe.get(0);
	
	
		
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
	
	public void setAktuellerMitarbeiter(model.Mitarbeiter neuerMitarbeiter) {
		System.out.println("Änderung");
		System.out.println(neuerMitarbeiter);
		System.out.println("davor " + aktuellerMitarbeiter);
		this.aktuellerMitarbeiter = neuerMitarbeiter;
		System.out.println("danach "+  aktuellerMitarbeiter);

		/*System.out.println(aktuellerMitarbeiter.getMitarbeiterVorn());*/
		}
	
	public List<String> getMitarbeiterString() {
		return mitarbeiterString;
	}
	

	

	public void onChange() {
        System.out.println("Aktueller Mitarbeiter: ");

	    if (aktuellerMitarbeiter != null) {
	        System.out.println("Aktueller Mitarbeiter: " + aktuellerMitarbeiter.getMitarbeiterVorn());
	    } else {
	        System.out.println("Kein Mitarbeiter ausgewählt.");
	    }
	}
	
	

	
	
	
	
	
}
