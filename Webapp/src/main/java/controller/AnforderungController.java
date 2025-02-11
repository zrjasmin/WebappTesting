package controller;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;


@Named
@SessionScoped
public class AnforderungController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	service.AnforderungService service;
	
	@Inject 
	dao.AnforderungDao anfDao;
	
	private model.Anforderung selectedAnf;
	private List<model.Akzeptanzkriterium> neueKriterien = new ArrayList<model.Akzeptanzkriterium>();

	public AnforderungController() {
		
	}
	
	
	public model.Anforderung getAnforderung(int index) {
		return service.getAnfListe().get(index);
	}
	
	public void saveMitarbeiter() {
		
	}
	
	
	
	public String bearbeiten(String anf) {
		if(anf.contains("neu")) {
			setSelectedAnf(new model.Anforderung());
			selectedAnf.setAnfNr(service.generateNumber());;
			if(neueKriterien != null) {
				neueKriterien.clear();
			}
			

		} else {
			neueKriterien.clear();
			neueKriterien.addAll(selectedAnf.getAnfKriterien());
			System.out.println("Anforderung " + selectedAnf.getAnfId());
		}
		
		
		return "edit.xhtml?faces-redirect=true";
	}
	
	public model.Anforderung getSelectedAnf() {
		return selectedAnf;
	}
	
	public void setSelectedAnf(model.Anforderung selectedAnf) {
		this.selectedAnf = selectedAnf;
	}
	
	
	/*
	public String loadDetails(Integer anfId) {
		
		
		System.out.println("Anforderung wird geladen");
		if(selectedAnf == null) {
			selectedAnf = new model.Anforderung();	
			
		}
		setSelectedAnf(anfDao.findAnf(anfId));	
		System.out.println(selectedAnf.getAnfId());
		
		return "/detail.xhtml?faces-redirect=true";
	}
	*/
	
	public String selectAnforderung(Integer id) {
		selectedAnf = anfDao.findAnf(id);
		
		System.out.println("ausgewählte Anforderung: " + selectedAnf.getAnfId());
		return "detail?faces-redirect=true&id=" + selectedAnf.getAnfId();
			
	}
	
	
	public String zurueck() {
		return "anforderungen?faces-redirect=true";
	}
	
	public String showAnfNr() {
		String nummer;
		if(selectedAnf == null) {
			nummer = service.generateNumber();
			System.out.println("nummer "+nummer);
		} else {
			nummer = selectedAnf.getAnfNr();
		}
		
		return nummer;
	}
	
	public List<model.Akzeptanzkriterium> getNeueKriterien() {
		return neueKriterien;
	}
	
	public void addKriterium() {
		neueKriterien.add(new model.Akzeptanzkriterium());
	}
	
	
	
	
	public String updateOrCreate(){
		Integer anfId;
		String pagename;
		
		//updatet bestehende Anforderung
		if(anfDao.exist(selectedAnf.getAnfId())) {
			System.out.println("wir müssen die anforderungen updaten");
			service.anfUpdaten(selectedAnf, neueKriterien);
			
			anfId = selectedAnf.getAnfId();
		} else  { 
			// erstellt neue Anforderung
	
			service.anfErstellen(selectedAnf, neueKriterien);
			anfId = selectedAnf.getAnfId();
			selectedAnf = new model.Anforderung();
			neueKriterien = new ArrayList<model.Akzeptanzkriterium>();
			
		}		
		pagename = "/detail.xhtml?id="+ anfId.toString();
		return pagename;

	}
	
	public String deleteKriterium(model.Akzeptanzkriterium kriterium) {
		neueKriterien.remove(kriterium);
		anfDao.deleteKriteriumFromAnf(kriterium, getSelectedAnf());
		return "";
	}
	
	
	
	
	

}