package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class TestfallService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	dao.TestfallDao testDao;
	@Inject 
	controller.ArbeiterController arbeiterController;
	
	public static final List<model.Testfall> testListe = new ArrayList<>();
	
	
	public TestfallService() {
		testDao = new dao.TestfallDao();
		/*model.Testfall t1 = new model.Testfall("hdh", "hh", "hfh", "hh");
		testListe.add(t1);*/
		testListe.addAll(getTestListe());
		//testDao.addTest(1, t1);
	}

	public List<model.Testfall> getTestListe() {
		return testDao.findAll();
	}

	
	public String generateNumber() {
		String maxNr = testDao.maxTestNr();
		int nextNumber = 1;

		if(maxNr != null) {
			String number = maxNr.substring(3);
			
			if (!number.isEmpty()) {
	            try {
	                nextNumber = Integer.parseInt(number) + 1; // Erhöhe den Wert um 1
	            } catch (NumberFormatException e) {
	                System.err.println("Fehler beim Parsen der Nummer: " + e.getMessage());
	                // Hier kannst du entscheiden, wie du mit dem Fehler umgehen möchtest
	               
	            }
	        }
			
			
		}
		
	
        
		
		return String.format("TF-%03d", nextNumber);
	}
	
	
	public void anfErstellen(model.Testfall neuerTest) {
			
			System.out.println("service:" + arbeiterController.getAktuellerMitarbeiter().getVorname());
	
			neuerTest.setErsteller(arbeiterController.getAktuellerMitarbeiter());
			
			neuerTest.setNr(generateNumber());
			testDao.addTest(neuerTest.getErsteller().getArbeiterId(), neuerTest);
		}

	public void testUpdaten(model.Testfall test) {
		System.out.println("wir updatet die Anforderung");
		//test.setAnfKriterien(kriterien);
		
		testDao.updateTest(test);
	}
	
	
	
	

}