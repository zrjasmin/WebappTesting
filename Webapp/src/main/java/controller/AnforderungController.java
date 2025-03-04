package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
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
	
	private Integer selectedId;
	private model.Anforderung selectedAnf;
	private List<model.Akzeptanzkriterium> neueKriterien = new ArrayList<model.Akzeptanzkriterium>();


	
	public AnforderungController() {
		selectedAnf = new model.Anforderung();
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
	
	

	
	public String selectAnforderung(Integer id) {
		selectedAnf = anfDao.findAnf(id);
		selectedId = id;
		System.out.println("ausgewählte Anforderung: " + selectedAnf.getAnfId());
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
	
	
	public String deleteAnf() {
		anfDao.deleteAnf(selectedAnf);
		return "/anforderung.xhtml?faces-redirect=true";
	}
	
	

	

	public Integer getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(Integer selectedId) {
		this.selectedId = selectedId;
	}
	
	
	
	
	

}