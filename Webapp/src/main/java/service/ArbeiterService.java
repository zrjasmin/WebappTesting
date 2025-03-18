package service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private model.Arbeiter aktuellerMitarbeiter;

    private Map<Integer, model.Arbeiter> arbeiterAsMap;

	public static List<model.Arbeiter> arbeiterListe = new ArrayList<>();
	
	
	
	public ArbeiterService() {
		dao = new dao.ArbeiterDao();
		/*model.Arbeiter arbeiter1 = new model.Arbeiter("Jasmin", "Zimmer", "email", "url");
		model.Arbeiter arbeiter2 = new model.Arbeiter("Laura", "Zimmer", "email", "url");
		arbeiterListe.add(arbeiter2);
		dao.createArbeiter(arbeiter2);
		arbeiterListe.add(arbeiter1);
		dao.createArbeiter(arbeiter1);
		System.out.println(arbeiterListe);*/
		arbeiterAsMap = dao.alleArbeiter();
		
		
		aktuellerMitarbeiter = new model.Arbeiter();

		onSubmit();

	}
	
	
	public List<model.Arbeiter> getArbeiterListe() {
		return arbeiterListe;
	}
	
	public model.Arbeiter getAktuellerMitarbeiter() {
		return aktuellerMitarbeiter;
	}
	
	public void setAktuellerMitarbeiter(model.Arbeiter arbeiter) {
		this.aktuellerMitarbeiter = arbeiter;
		System.out.println("geänderter Mitarbeiter:" + arbeiter.getArbeiterId());
	}
	
	public void onSubmit() {
		System.out.println("Ausgewählter Arbeiter: ");

	
	
	
	
	
	}}
