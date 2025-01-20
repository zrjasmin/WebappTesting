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
		
		
		anfListe.addAll(getAnfListe());
	
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
	
	public void speichern(model.Anforderung neuerArtikel) {
		model.Mitarbeiter currentMitarbeiter = arbeiterService.getAktuellerMitarbeiter();
		
		anfDao.saveAnf(currentMitarbeiter.getMitarbeiterId(), neuerArtikel);
	
	}
	


	
	
}