package controller;

import java.io.Serializable;
import jakarta.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.Anforderung;

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
	@Inject 
	controller.ArbeiterController arbeiterController;
	
	private model.Anforderung selectedAnf;	
	private Integer selectedId;
	
	private  List<model.Anforderung> alleAnf = new ArrayList<>();	
	private List<model.Anforderung> verknüpfteAnf = new ArrayList<model.Anforderung>();
	private List<model.Akzeptanzkriterium> neueKriterien = new ArrayList<model.Akzeptanzkriterium>();
	
	@PostConstruct
	 public void init() {
	
		String idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
        if (idParam != null) {
            try {
            	alleAnf.clear();
            	alleAnf.addAll(anfDao.findAll());
            	selectedId = Integer.valueOf(idParam);
            	selectedAnf = anfDao.findAnf(selectedId);
            	neueKriterien.clear();
            	
            	verknüpfteAnf.clear();
            	
            	if(selectedAnf != null) {
                	neueKriterien.addAll(selectedAnf.getAnfKriterien());
                	verknüpfteAnf.addAll(selectedAnf.getVerknüpfteAnforderungen());


            	}
            	
    
    	       
            } catch (NumberFormatException e) {
             
            }
        }

	}
	

	public EditController() {
		anfController = new controller.AnforderungController();
		selectedAnf = new model.Anforderung();
	}

	public model.Anforderung getSelectedAnf() {
		return selectedAnf;
	}
	
	public void setSelectedAnf(model.Anforderung anf) {
		this.selectedAnf = anf;
	}
	
	
	
	
	public String updateOrCreate(){	
		String redirect;
	
		if(selectedAnf.getAnfId() == null || !(anfDao.exist(selectedId))) {
			neueAnfSpeichern();
			
			Integer lastCreated = anfDao.getLetzteAnf().getAnfId();
			redirect = "detail?faces-redirect=true&id=" + lastCreated;	
		} else {
			service.anfUpdaten(selectedAnf, neueKriterien, verknüpfteAnf);
			verknüpfteAnf.clear();
			
			redirect =  "detail?faces-redirect=true&id=" + selectedAnf.getAnfId();
		}
		
		return redirect;
	}

	
	
	public void neueAnfSpeichern() {
	
		service.anfErstellen(selectedAnf, neueKriterien, verknüpfteAnf);
		selectedAnf = new model.Anforderung();
		neueKriterien.clear();
		verknüpfteAnf.clear();

		}
	
	//umleiten zu Edit.xhtml
	public String bearbeiten() {
		
		return "edit.xhtml?faces-redirect=true&id=" + selectedAnf.getAnfId();
	}
	
	
	
	
	public List<model.Akzeptanzkriterium> getNeueKriterien() {
		return neueKriterien;
	}
	
	public void addKriterium() {
		neueKriterien.add(new model.Akzeptanzkriterium());
	}
	
	public String deleteKriterium(model.Akzeptanzkriterium kriterium) {
		neueKriterien.remove(kriterium);
		anfDao.deleteKriterium(kriterium.getId());
		return "";
	}


	public List<model.Anforderung> getVerknüpfteAnf() {
		return verknüpfteAnf;
	}


	public void setVerknüpfteAnf(List<model.Anforderung> verknüpfteAnf) {
		System.out.println("setzen der verknüpfunge:" + verknüpfteAnf);
		this.verknüpfteAnf = verknüpfteAnf;
	}

	public List<model.Anforderung> getAlleAnf() {
		return alleAnf;
	}
	
	public void setAlleAnf(List<model.Anforderung> anf) {
		this.alleAnf = anf;
		
	}
	
	
	}
	