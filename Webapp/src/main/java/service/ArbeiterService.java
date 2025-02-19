package service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ArbeiterService implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private dao.ArbeiterDao dao;
	
	private model.Arbeiter arbeiter = new model.Arbeiter();
	private model.Arbeiter aktuellerMitarbeiter = new model.Arbeiter();
	public static List<model.Arbeiter> arbeiterListe = new ArrayList<>();
	
	
	
	public ArbeiterService() {
		dao = new dao.ArbeiterDao();
		//model.Arbeiter arbeiter2 = new model.Arbeiter("laura", "zimmer", "email", "url");
		//arbeiterListe.add(arbeiter2);
		//dao.createArbeiter(arbeiter2);
		arbeiterListe.addAll(dao.alleArbeiter());
	}
	
	
	public List<model.Arbeiter> getArbeiterListe() {
		return arbeiterListe;
	}
	
	public model.Arbeiter getAktuellerMitarbeiter() {
		return aktuellerMitarbeiter;
	}
	
	public void setAktuellerMitarbeiter(model.Arbeiter arbeiter ) {
		this.aktuellerMitarbeiter = arbeiter;
	}
	}
