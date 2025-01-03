package model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

@Entity
@Named
@RequestScoped
public class Voraussetzung {
	private int vorID;
	private Testfall testfallID;
	private String vorBeschreibung;
	
	
	public int getVorID() {
		return vorID;
	}
	public void setVorID(int vorID) {
		this.vorID = vorID;
	}
	public Testfall getTestfallID() {
		return testfallID;
	}
	public void setTestfallID(Testfall testfallID) {
		this.testfallID = testfallID;
	}
	public String getVorBeschreibung() {
		return vorBeschreibung;
	}
	public void setVorBeschreibung(String vorBeschreibung) {
		this.vorBeschreibung = vorBeschreibung;
	}
}