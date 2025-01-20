package controller;
import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
@SessionScoped 
public class AnforderungController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	service.AnforderungService service;
	
	@Inject 
	dao.AnforderungDao anfDao;
	
	private model.Anforderung selectedAnf;
	private model.Anforderung neueAnf = new model.Anforderung();

	public AnforderungController() {
		
	}
	
	
	public model.Anforderung getAnforderung(int index) {
		return service.getAnfListe().get(index);
	}
	
	public void saveMitarbeiter() {
		
	}
	
	
	public String create() {
		return "edit.xhtml";
	}
	public String edit() {
		return "edit.xhtml";
	}
	
	public model.Anforderung getSelectedAnf() {
		return selectedAnf;
	}
	
	public void setSelectedAnf(model.Anforderung selectedAnf) {
		this.selectedAnf = selectedAnf;
	}
	
	
	
	public String loadDetails(Integer anfId) {
		if(selectedAnf == null) {
			selectedAnf = new model.Anforderung();	
		}
		setSelectedAnf(anfDao.findAnf(anfId));		
		
		return "/detail.xhtml?faces-redirect=true";
	}
	
	public String zurueck() {
		return "anforderungen?faces-redirect=true";
	}
	
	public model.Anforderung getNeueAnf() {
		return neueAnf;
	}
	
	public void setNeueAnf(model.Anforderung neueAnf) {
		this.neueAnf = neueAnf;
	}
	

	
	
	public void createNeueAnforderung() {
	
		
		service.speichern(neueAnf);
		neueAnf = new model.Anforderung();
	}
	
	

}