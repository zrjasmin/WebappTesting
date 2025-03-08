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
	
	@PostConstruct
	 public void init() {
		
		String idParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
       if (idParam != null) {
           try {
           	selectedId = Integer.valueOf(idParam);
           	setSelectedTest(testDao.findTest(selectedId));
   	        System.out.println("initialisierung: ID " + selectedId);
           
           } catch (NumberFormatException e) {
            
           }
       }
}
	
	public TestEditController() {
		selectedTest = new model.Testfall();
	}

	public model.Testfall getSelectedTest() {
		return selectedTest;
	}

	public void setSelectedTest(model.Testfall selectedTest) {
		this.selectedTest = selectedTest;
	}
	
	public String bearbeiten() {
		//neueKriterien.clear();
		//neueKriterien.addAll(selectedAnf.getAnfKriterien());
		
		System.out.println("Anforderung bearbeiten " + selectedTest.getTestId() );
	
	return "editTestfall.xhtml?faces-redirect=true&id=" + selectedTest.getTestId();
}
	
	
	public String updateOrCreate(){	
		String redirect;
		System.out.println("Updaten oder Speichern");
		
		if(selectedTest.getTestId() == null || !(testDao.exist(selectedId))) {
			//selectedAnf
			System.out.println("selectedID: "+  selectedId);
			testSpeichern();
			
			Integer lastCreated = testDao.getLetzteAnf().getTestId();
			System.out.println(lastCreated);
			
			redirect = "detailTestfall?faces-redirect=true&id=" + lastCreated;
					
			System.out.println("Testfall wird neu erstellt");
		} else {
			
			service.testUpdaten(selectedTest);
			redirect =  "detailTestfall?faces-redirect=true&id=" + selectedTest.getTestId();
		}
	
		return redirect;
	}
	
	public void testSpeichern() {
		
		System.out.println("neue Anforderung wird erstellt");
		System.out.println(selectedId);

		
		service.anfErstellen(selectedTest);
		selectedTest = new model.Testfall();
		
		
		
		}
}