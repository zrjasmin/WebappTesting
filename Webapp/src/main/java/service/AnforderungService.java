package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.AnforderungDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import model.Akzeptanzkriterium;
import model.Anforderung;
import model.Mitarbeiter.Rolle;

import java.io.Serializable;

@Named
@SessionScoped
public class AnforderungService implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private model.Anforderung anf = new model.Anforderung();
	
	@Inject
	private dao.AnforderungDao anfDao;
	
	@Inject 
	private service.MitarbeiterService arbeiterService;
	
	@Inject 
	private controller.AnforderungController controller;
	

	public static final List<model.Anforderung> anfListe = new ArrayList<>();
	

	
	private static List<model.Akzeptanzkriterium> kriterien = new ArrayList<model.Akzeptanzkriterium>();
	

	public  AnforderungService() {
		anfDao = new dao.AnforderungDao();
		arbeiterService = new service.MitarbeiterService();
		/*
		model.Akzeptanzkriterium k1 = new model.Akzeptanzkriterium("Alle Produkte müssen mit Bild, Name und Preis angezeigt werden");
		model.Akzeptanzkriterium k2 = new model.Akzeptanzkriterium("Die Produkte sollen nach Kategorien filterbar sein");
		kriterien.add(k1);
		kriterien.add(k2);
		model.Anforderung a1 = new model.Anforderung("AR-004", "Produktkatalog anzeigen","Die Plattform soll einen Katalog aller verfügbaren Produkte anzeigen", 
				" Benutzern ermöglichen, Produkte zu durchsuchen und auszuwählen.", "Hoch", kriterien );
		anfListe.add(a1);
		
		anfListe.addAll(getAnfListe());
		
		anfDao.saveAnf(2, a1);
		*/
	
	}
	
	public List<model.Anforderung> getAnfListe() {
		return anfDao.findAll();
	}
	


	public model.Anforderung getAnf() {
		return anf;
	}
	
	public void setAnf(model.Anforderung anforderung) {
		this.anf = anforderung;
	}
	
	
	public void speichern(model.Anforderung neuerArtikel, List<model.Akzeptanzkriterium> kriterien) {
		
		model.Mitarbeiter currentMitarbeiter = arbeiterService.getAktuellerMitarbeiter();
		neuerArtikel.setAnfKriterien(kriterien);
		neuerArtikel.setAnfNr(generateNumber());
		
		
		anfDao.saveAnf(currentMitarbeiter.getMitarbeiterId(), neuerArtikel);
	
	}
	
	public String generateNumber() {
		int size = anfDao.findAll().size();
		String anfNummer;
		if(String.valueOf(size).length() == 2) {
			anfNummer = "AR-0" + size;
		} else {
			anfNummer = "AR-"+size;
		}
		return anfNummer;
	}
	
	/*Validierung der Eingabemaske*/
	

	
	
}