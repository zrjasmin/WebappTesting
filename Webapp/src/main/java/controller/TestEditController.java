package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class TestEditController implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	@Inject 
	dao.TestfallDao testDao;
	@Inject
	service.TestfallService service;
	
	private model.Testfall selectedTest;	
	private Integer selectedId;
	private List<model.Testschritte> schritte = new ArrayList<model.Testschritte>();
//	private List<model.Voraussetzung> voraussetzungen = new ArrayList<model.Voraussetzung>();
	
	@PostConstruct
	 public void init() {
		
		String idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
       if (idParam != null) {
           try {
           	selectedId = Integer.valueOf(idParam);
           	setSelectedTest(testDao.findTest(selectedId));
           	schritte.addAll(selectedTest.getTestschritte());
        //   	voraussetzungen.addAll(selectedTest.getVoraussetzungen());
   	        System.out.println("initialisierung: ID " + selectedId);
   	       
           
           } catch (NumberFormatException e) {
            
           }
       }
}
	
	public TestEditController() {
		selectedTest = new model.Testfall();
	}

	public model.Testfall getSelectedTest() {
		System.out.println(selectedTest.getErwartetesErgebnis());
		return selectedTest;
	}

	public void setSelectedTest(model.Testfall selectedTest) {
		this.selectedTest = selectedTest;
	}
	
	public String bearbeiten() {
		schritte.clear();
		schritte.addAll(selectedTest.getTestschritte());
		//voraussetzungen.clear();
		//voraussetzungen.addAll(selectedTest.getVoraussetzungen());

		System.out.println("Anforderung bearbeiten " + selectedTest.getTestId());
	
	return "editTestfall.xhtml?faces-redirect=true&id=" + selectedTest.getTestId();
}
	
	
	public String updateOrCreate(){	
		String redirect;
		System.out.println("Updaten oder Speichern");
		
		if(selectedTest.getTestId() == null || !(testDao.exist(selectedId))) {
			testSpeichern();
			Integer lastCreated = testDao.getLetzteAnf().getTestId();
			redirect = "detailTestfall?faces-redirect=true&id=" + lastCreated;
					
		} else {
			service.testUpdaten(selectedTest, schritte);
			redirect =  "detailTestfall?faces-redirect=true&id=" + selectedTest.getTestId();
		}
	
		return redirect;
	}
	
	public void testSpeichern() {
		
		System.out.println("neue Anforderung wird erstellt");
		System.out.println(selectedId);

		
		service.anfErstellen(selectedTest, schritte);
		selectedTest = new model.Testfall();
		schritte.clear();
		//voraussetzungen.clear();
		
		
		}
	
	
	public List<model.Testschritte> getSchritte() {
		return schritte;
	}
	
	
	
	public void addSchritt() {
		schritte.add(new model.Testschritte());
	}
	
	public String deleteSchritt(model.Testschritte schritt) {
		schritte.remove(schritt);
		testDao.deleteSchritt(schritt.getId());
		return "";
	}

	/*public List<model.Voraussetzung> getVoraussetzungen() {
		return voraussetzungen;
	}

	public void setVoraussetzungen(List<model.Voraussetzung> voraussetzungen) {
		this.voraussetzungen = voraussetzungen;
	}
	
	
	public void addVoraussetzung() {
		voraussetzungen.add(new model.Voraussetzung());
	}
	
	public String deleteVoraussetzung(model.Voraussetzung voraussetzung) {
		voraussetzungen.remove(voraussetzung);
		testDao.deleteVoraussetzung(voraussetzung.getId());
		return "";
	}
	*/
	
}