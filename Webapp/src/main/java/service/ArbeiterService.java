package service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	
	
	private model.Arbeiter aktuellerMitarbeiter;

  

	public static List<model.Arbeiter> arbeiterListe = new ArrayList<>();
	
	
	public ArbeiterService() {
		dao = new dao.ArbeiterDao();
		/*String[] reBerechtigung = {"ANFORDERUNGEN_ERSTELLEN","ANFORDERUNGEN_AENDERN", "ANFORDERUNGEN_LOESCHEN"};
		model.Rolle re = new model.Rolle("RE", reBerechtigung);
		
		String[] testerBerechtigung = {};
		model.Rolle tester = new model.Rolle("TESTER", testerBerechtigung);
		
		String[] testfallErstellerBerechtigung = {"TESTFAELLE_ERSTELLEN", "TESTFAELLE_AENDERN", "TESTFAELLE_LOESCHEN"};
		model.Rolle testfallErsteller = new model.Rolle("TESTFALLERSTELLER", testfallErstellerBerechtigung);
		
		String[] managerBerechtigung = {};
		model.Rolle manager = new model.Rolle("MANAGER", managerBerechtigung);
		
		
		dao.saveRolle(testfallErsteller);
		dao.saveRolle(re);
		dao.saveRolle(tester);
		dao.saveRolle(manager);
*/
		/*model.Arbeiter arbeiter1 = new model.Arbeiter("Tester", "ee", "email", "url");
	
		model.Arbeiter arbeiter2 = new model.Arbeiter("RE", "ee", "email", "url");
		
		model.Arbeiter arbeiter3 = new model.Arbeiter("Manager", "ee", "email", "url");
	
		model.Arbeiter arbeiter4 = new model.Arbeiter("Testfallersteller", "ee", "email", "url");
		
		
		dao.createArbeiter(arbeiter4);
		dao.createArbeiter(arbeiter2);

		dao.createArbeiter(arbeiter3);
		dao.createArbeiter(arbeiter1);
		*/
		arbeiterListe.clear();
		arbeiterListe.addAll(getArbeiterListe());
		
		
		
		aktuellerMitarbeiter = new model.Arbeiter();


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
	
	
	//Berechtigungen managen
	
	
	
	
}
	


