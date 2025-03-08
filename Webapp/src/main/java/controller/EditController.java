package controller;

import java.io.Serializable;
import jakarta.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class EditController implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	
	@Inject 
	controller.AnforderungController anfController;
	@Inject
	dao.AnforderungDao anfDao;
	@Inject 
	service.AnforderungService service;
	 
	
	private model.Anforderung selectedAnf;	
	private Integer selectedId;
	private List<model.Akzeptanzkriterium> neueKriterien = new ArrayList<model.Akzeptanzkriterium>();
	
	@PostConstruct
	 public void init() {
		
		String idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
        if (idParam != null) {
            try {
            	selectedId = Integer.valueOf(idParam);
            	selectedAnf = anfDao.findAnf(selectedId);
            	neueKriterien.addAll(selectedAnf.getAnfKriterien());
    	        System.out.println("initialisierung: ID " + selectedId);
            
            } catch (NumberFormatException e) {
             
            }
        }

	}
	

	public EditController() {
		anfController = new controller.AnforderungController();
		selectedAnf = new model.Anforderung();
		System.out.println("Im DetailController");
		System.out.println("ID: "+ anfController.getSelectedId());

	}

	public model.Anforderung getSelectedAnf() {
		return selectedAnf;
	}
	
	public void setSelectedAnf(model.Anforderung anf) {
		this.selectedAnf = anf;
	}
	
	
	
	
	public String updateOrCreate(){	
		String redirect;
		System.out.println("Updaten oder Speichern");
		
		if(selectedAnf.getAnfId() == null || !(anfDao.exist(selectedId))) {
			//selectedAnf
			System.out.println("selectedID: "+  selectedId);
			neueAnfSpeichern();
			
			Integer lastCreated = anfDao.getLetzteAnf().getAnfId();
			System.out.println(lastCreated);
			
			redirect = "detail?faces-redirect=true&id=" + lastCreated;
					
			System.out.println("Anforderung wird neu erstellt");
		} else {
			System.out.println("wir müssen die anforderungen updaten" + selectedAnf.getAnfNr());
			System.out.println("kriterien zum speichern" + neueKriterien);
			service.anfUpdaten(selectedAnf, neueKriterien);
			redirect =  "detail?faces-redirect=true&id=" + selectedAnf.getAnfId();
		}
		System.out.println("Link: ");
		return redirect;
	}

	
	
	public void neueAnfSpeichern() {
	
		System.out.println("neue Anforderung wird erstellt");
		System.out.println(selectedId);

		
		service.anfErstellen(selectedAnf, neueKriterien);
		selectedAnf = new model.Anforderung();
		neueKriterien.clear();
		
		
		
		}
	
	
	
	
	
	
	public String bearbeiten() {
		//neueKriterien.clear();
		//neueKriterien.addAll(selectedAnf.getAnfKriterien());
		
		System.out.println("Anforderung bearbeiten " + selectedAnf.getAnfId() );
	
	return "edit.xhtml?faces-redirect=true&id=" + selectedAnf.getAnfId();
}
	
	public List<model.Akzeptanzkriterium> getNeueKriterien() {
		return neueKriterien;
	}
	
	public void addKriterium() {
		neueKriterien.add(new model.Akzeptanzkriterium());
		System.out.println("neues kriterium erstellt");
	}
	
	public String deleteKriterium(model.Akzeptanzkriterium kriterium) {
		neueKriterien.remove(kriterium);
		anfDao.deleteKriterium(kriterium.getId());
		return "";
	}

	
	
	
	
	}
	