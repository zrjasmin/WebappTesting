package model;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

@Entity
@Named
@RequestScoped
public class Testfall {
	private int testfallID;
	private String testfallNr;
	private String testfallName;
	private String testfallZiel;
	private String testfallStatus;
	private Mitarbeiter testfallTesterID;
	private Mitarbeiter testfallErstellerID;
	private List<Voraussetzung> testfallVorauss;
	private List<Testschritt> testSchritte;
	private String testfallRisiko;
	private Testlauf testlaufID;
	
	public int getTestfallID() {
		return testfallID;
	}
	public void setTestfallID(int testfallID) {
		this.testfallID = testfallID;
	}
	public String getTestfallNr() {
		return testfallNr;
	}
	public void setTestfallNr(String testfallNr) {
		this.testfallNr = testfallNr;
	}
	public String getTestfallZiel() {
		return testfallZiel;
	}
	public void setTestfallZiel(String testfallZiel) {
		this.testfallZiel = testfallZiel;
	}
	public String getTestfallName() {
		return testfallName;
	}
	public void setTestfallName(String testfallName) {
		this.testfallName = testfallName;
	}
	public String getTestfallStatus() {
		return testfallStatus;
	}
	public void setTestfallStatus(String testfallStatus) {
		this.testfallStatus = testfallStatus;
	}
	public Mitarbeiter getTestfallTesterID() {
		return testfallTesterID;
	}
	public void setTestfallTesterID(Mitarbeiter testfallTesterID) {
		this.testfallTesterID = testfallTesterID;
	}
	public List<Voraussetzung> getTestfallVorauss() {
		return testfallVorauss;
	}
	public void setTestfallVorauss(List<Voraussetzung> testfallVorauss) {
		this.testfallVorauss = testfallVorauss;
	}
	public List<Testschritt> getTestSchritte() {
		return testSchritte;
	}
	public void setTestSchritte(List<Testschritt> testSchritte) {
		this.testSchritte = testSchritte;
	}
	public Mitarbeiter getTestfallErstellerID() {
		return testfallErstellerID;
	}
	public void setTestfallErstellerID(Mitarbeiter testfallErstellerID) {
		this.testfallErstellerID = testfallErstellerID;
	}
	public String getTestfallRisiko() {
		return testfallRisiko;
	}
	public void setTestfallRisiko(String testfallRisiko) {
		this.testfallRisiko = testfallRisiko;
	}
	public Testlauf getTestlaufID() {
		return testlaufID;
	}
	public void setTestlaufID(Testlauf testlaufID) {
		this.testlaufID = testlaufID;
	}
	
	
}