package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ArbeiterController implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	@Inject
	dao.ArbeiterDao dao;
	
	@Inject 
	service.ArbeiterService service;
	
	private String selectedOption = new String();
    private Integer selectedArbeiterId; // Ausgewählte ID
    private model.Arbeiter aktuellerMitarbeiter;
    
	private Map<Integer, model.Arbeiter> arbeiter = new HashMap<>();

	public ArbeiterController() {
		dao = new dao.ArbeiterDao();
		arbeiter = dao.alleArbeiter();
		if(!arbeiter.isEmpty()) {
			aktuellerMitarbeiter = arbeiter.get(0);
		}

		}
	
	public void change() {
		System.out.println("change Methode");
		System.out.println(aktuellerMitarbeiter);

	
	}
	
	public void submit() {
		
		System.out.println("Aktueller Mitarbeiter: " );
		  

	}
	
	public void onArbeiterSelect() {
        System.out.println("Ausgewählter Arbeiter ID: " + aktuellerMitarbeiter.getArbeiterId());
        // Hier kannst du weitere Logik hinzufügen
    }
	

	public model.Arbeiter getAktuellerMitarbeiter() {
		return aktuellerMitarbeiter;
	}
	
	public void setAktuellerMitarbeiter(model.Arbeiter aktuellerMitarbeiter) {
		this.aktuellerMitarbeiter = aktuellerMitarbeiter;
	}



    public Map<Integer, model.Arbeiter> getArbeiter() {
        return arbeiter;
    }

	public void setArbeiter(Map<Integer,model.Arbeiter> arbeiter) {
		this.arbeiter = arbeiter;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	
	  public Integer getSelectedArbeiterId() {
	        return selectedArbeiterId;
	    }

	    public void setSelectedArbeiterId(Integer selectedArbeiterId) {
	        this.selectedArbeiterId = selectedArbeiterId;
	    }


}

	
	
	
	

