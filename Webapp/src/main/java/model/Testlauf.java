package model;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Named
@RequestScoped
public class Testlauf implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int testlaufId;
	private String testlaufNr;
	private String testaufBeschreibung;
	private Mitarbeiter testlaufTester;
	private Mitarbeiter testlaufErsteller;
	private String testumgebung;
	private List<Testfall> testfälle;
	private String testlaufPrio;
	private List<Testfall> testlaufErg;
	
	
	
	public Testlauf() {
	}
	public Testlauf(String testlaufNr, String Beschreibung, String Umgebung) { 
		this.testlaufNr = testlaufNr;
		this.setTestaufBeschreibung(Beschreibung);
		this.setTestumgebung(Umgebung);
	}
	
	public String getTestlaufNr() {
		return testlaufNr;
	}
	public void setTestNr(String testlaufNr) {
		this.testlaufNr = testlaufNr;
	}
	public String getTestumgebung() {
		return testumgebung;
	}
	public void setTestumgebung(String testumgebung) {
		this.testumgebung = testumgebung;
	}
	public String getTestaufBeschreibung() {
		return testaufBeschreibung;
	}
	public void setTestaufBeschreibung(String testaufBeschreibung) {
		this.testaufBeschreibung = testaufBeschreibung;
	}
	public Mitarbeiter getTestlaufTester() {
		return testlaufTester;
	}
	public void setTestlaufTester(Mitarbeiter testlaufTester) {
		this.testlaufTester = testlaufTester;
	}
	public Mitarbeiter getTestlaufErsteller() {
		return testlaufErsteller;
	}
	public void setTestlaufErsteller(Mitarbeiter testlaufErsteller) {
		this.testlaufErsteller = testlaufErsteller;
	}
	public List<Testfall> getTestfälle() {
		return testfälle;
	}
	public void setTestfälle(List<Testfall> testfälle) {
		this.testfälle = testfälle;
	}
	public String getTestlaufPrio() {
		return testlaufPrio;
	}
	public void setTestlaufPrio(String testlaufPrio) {
		this.testlaufPrio = testlaufPrio;
	}
	public List<Testfall> getTestlaufErg() {
		return testlaufErg;
	}
	public void setTestlaufErg(List<Testfall> testlaufErg) {
		this.testlaufErg = testlaufErg;
	}
	
	
}