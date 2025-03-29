package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	private controller.ArbeiterController arbeiterController;

	public static final List<model.Anforderung> anfListe = new ArrayList<>();	
	private Map<Integer, model.Anforderung> anfAsMap;

	public  AnforderungService() {
		anfDao = new dao.AnforderungDao();
		
		
		anfListe.clear();
		anfListe.addAll(getAnfListe());
		
	
	}
	
	//Laden aus der Datenbank
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
	public void anfErstellen(model.Anforderung neuerArtikel, List<model.Akzeptanzkriterium> kriterien, List<model.Anforderung> verknüpfteAnf) {
		
		neuerArtikel.setErsteller(arbeiterController.getAktuellerMitarbeiter());
		
		neuerArtikel.setAnfKriterien(kriterien);
		
		neuerArtikel.setAnfNr(generateNumber());

	
		System.out.println("service: " + verknüpfteAnf);

		anfDao.saveAnf(neuerArtikel.getErsteller().getArbeiterId(), neuerArtikel);
		System.out.println("service: " + verknüpfteAnf);
		anfDao.manageVerknüpfteAnforderungen(neuerArtikel, verknüpfteAnf);
	}
	
	//updatet bestehende Anforderung
	public void anfUpdaten(model.Anforderung anf, List<model.Akzeptanzkriterium> kriterien, List<model.Anforderung> verknüpfteAnf) {		
		anf.setAnfKriterien(kriterien);
		anf.setVerknüpfteAnforderungen(verknüpfteAnf);
		anfDao.updateAnf(anf);
		anfDao.manageVerknüpfteAnforderungen(anf, verknüpfteAnf);

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

	public Map<Integer, model.Anforderung> getAnfAsMap() {
		if(anfAsMap == null) {
			anfAsMap = anfListe.stream().collect(Collectors.toMap(model.Anforderung::getAnfId, anforderung -> anforderung, (existing, replacement) -> existing));
		}
		return anfAsMap;
	}

	public void setAnfAsMap(Map<Integer, model.Anforderung> anfAsMap) {
		this.anfAsMap = anfAsMap;
	}
	
	
	
	/*Validierung der Eingabemaske*/
	

	
	
}