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
	
	private Integer selectedId;
	private model.Anforderung selectedAnf;
	private List<model.Akzeptanzkriterium> neueKriterien = new ArrayList<model.Akzeptanzkriterium>();

	public void init() {
        if (selectedId != null) {
        	selectedAnf = anfDao.findAnf(selectedId);
        } 
      
    }
	
	public AnforderungController() {
		
	}
	
	public model.Anforderung getAnforderung(int index) {
		return service.getAnfListe().get(index);
	}
	
	
	
	public model.Anforderung getSelectedAnf() {
		return selectedAnf;
	}
	
	public void setSelectedAnf(model.Anforderung selectedAnf) {
		this.selectedAnf = selectedAnf;
	}
	
	public Integer getSelectedId() {
		return selectedId;
	}
	
	public void setSelectedId(Integer id) {
		this.selectedId = id;
		init();
		
	}
	
	
	
	public String selectAnforderung(Integer id) {
		selectedAnf = anfDao.findAnf(id);
		
		System.out.println("ausgewählte Anforderung: ");
		return "detail?faces-redirect=true&id=" + selectedAnf.getAnfId();
			
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
		System.out.println("neues kriterium erstellt");
	}
	
	
	
	
	
	
	public String updateOrCreate(){	
		String redirect;
		if(selectedAnf.getAnfId() == null || anfDao.exist(selectedAnf.getAnfId())) {
			redirect = neueAnfSpeichern();
		} else {
			System.out.println("wir müssen die anforderungen updaten");
			service.anfUpdaten(selectedAnf, neueKriterien);
			redirect =  "detail?faces-redirect=true&id=" + selectedAnf.getAnfId();
		
		}
		return redirect;
	}
	


	//wechselt zur Bearbeiten Seite (neue Anforderung)
	public String neueAnfErstellen() {
		selectedAnf = new model.Anforderung();
		selectedAnf.setAnfNr(service.generateNumber());
		if(neueKriterien != null) {
			neueKriterien.clear();
		}
		System.out.println(selectedAnf.getAnfId());
		return "edit.xhtml?faces-redirect=true";
	}
	

	//Speichert neue Anforderung
	public String neueAnfSpeichern() {
		Integer redirectLink;
		System.out.println("neue Anforderung wird erstellt");
		service.anfErstellen(selectedAnf, neueKriterien);
		redirectLink = selectedAnf.getAnfId();
		selectedAnf = new model.Anforderung();
		neueKriterien = new ArrayList<model.Akzeptanzkriterium>();
		
		return "/detail.xhtml?faces-redirect=true&id=" + redirectLink;
		}
	
	public String deleteAnf() {
		anfDao.deleteAnf(selectedAnf);
		return "/anforderung.xhtml?faces-redirect=true";
	}
	
	
	public String bearbeiten() {
			neueKriterien.clear();
			neueKriterien.addAll(selectedAnf.getAnfKriterien());
			System.out.println("Anforderung " + selectedAnf.getAnfId());
		
		return "edit.xhtml?faces-redirect=true&id=" + selectedAnf.getAnfId();
	}
	
	public String deleteKriterium(model.Akzeptanzkriterium kriterium) {
		neueKriterien.remove(kriterium);
		anfDao.deleteKriteriumFromAnf(kriterium, getSelectedAnf());
		return "";
	}
	
	
	
	
	

}