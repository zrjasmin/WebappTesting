package model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

@Entity
@Named
@RequestScoped
public class Testschritt {
	private int schrittID;
	private Testfall testfallID;
	private String schrittBeschreibung;
	public int getSchrittID() {
		return schrittID;
	}
	public void setSchrittID(int schrittID) {
		this.schrittID = schrittID;
	}
	public Testfall getTestfallID() {
		return testfallID;
	}
	public void setTestfallID(Testfall testfallID) {
		this.testfallID = testfallID;
	}
	public String getSchrittBeschreibung() {
		return schrittBeschreibung;
	}
	public void setSchrittBeschreibung(String schrittBeschreibung) {
		this.schrittBeschreibung = schrittBeschreibung;
	}
	
	
	
}