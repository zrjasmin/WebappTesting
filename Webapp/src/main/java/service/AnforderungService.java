package service;

import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
	private controller.AnforderungController controller;
	

	public static final List<model.Anforderung> anfListe = new ArrayList<>();
	

	
	private static List<model.Akzeptanzkriterium> kriterien = new ArrayList<model.Akzeptanzkriterium>();
	

	public  AnforderungService() {
		
		anfDao = new dao.AnforderungDao();
		
		//	arbeiterService = new service.MitarbeiterService();
		/*
		model.Akzeptanzkriterium k1 = new model.Akzeptanzkriterium("Alle Produkte müssen mit Bild, Name und Preis angezeigt werden");
		model.Akzeptanzkriterium k2 = new model.Akzeptanzkriterium("Die Produkte sollen nach Kategorien filterbar sein");
		kriterien.add(k1);
		kriterien.add(k2);
		model.Anforderung a1 = new model.Anforderung("AR-004", "Produktkatalog anzeigen","Die Plattform soll einen Katalog aller verfügbaren Produkte anzeigen", 
				" Benutzern ermöglichen, Produkte zu durchsuchen und auszuwählen.", "Hoch", kriterien );
		anfListe.add(a1);
		*/
		anfListe.addAll(getAnfListe());
		
		//anfDao.saveAnf(2, a1);
		
	
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
	
	//erstellt neue Anforderung
	public void anfErstellen(model.Anforderung neuerArtikel, List<model.Akzeptanzkriterium> kriterien) {
		//Integer currentMitarbeiter = arbeiterService.getAktuellerMitarbeiter().getMitarbeiterId();
		neuerArtikel.setAnfKriterien(kriterien);
		neuerArtikel.setAnfNr(generateNumber());
		anfDao.saveAnf(1, neuerArtikel);
	}
	
	//updatet bestehende Anforderung
	public void anfUpdaten(model.Anforderung anf, List<model.Akzeptanzkriterium> kriterien) {
		System.out.println("wir updatet die Anforderung");
		anf.setAnfKriterien(kriterien);
		
		anfDao.updateAnf(anf);
	}
	
	

	
	public String generateNumber() {
		String maxNr = anfDao.maxAnfNr();
		int nextNumber = 1;
		
		if(maxNr != null) {
			String number = maxNr.substring(3);
			nextNumber = Integer.parseInt(number) + 1;
		}

		
		return String.format("AR-%03d", nextNumber);
	}
	
	
	
	/*Validierung der Eingabemaske*/
	

	
	
}