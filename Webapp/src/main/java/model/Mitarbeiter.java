package model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

@Entity
@Named
@RequestScoped
public class Mitarbeiter {
	
	private int mitarbeiterID;
	private String mitarbeiterVorname;
	private String mitarbeiterNachname;
	private String mitarbeiterEmail;
	private String mitarbeiterTel;
	private String mitarbeiterBild;
	private String mitarbeiterRolle;
	
	
	public int getMitarbeiterID() {
		return mitarbeiterID;
	}
	public void setMitarbeiterID(int mitarbeiterID) {
		this.mitarbeiterID = mitarbeiterID;
	}
	public String getMitarbeiterVorname() {
		return mitarbeiterVorname;
	}
	public void setMitarbeiterVorname(String mitarbeiterVorname) {
		this.mitarbeiterVorname = mitarbeiterVorname;
	}
	public String getMitarbeiterNachname() {
		return mitarbeiterNachname;
	}
	public void setMitarbeiterNachname(String mitarbeiterNachname) {
		this.mitarbeiterNachname = mitarbeiterNachname;
	}
	public String getMitarbeiterEmail() {
		return mitarbeiterEmail;
	}
	public void setMitarbeiterEmail(String mitarbeiterEmail) {
		this.mitarbeiterEmail = mitarbeiterEmail;
	}
	public String getMitarbeiterTel() {
		return mitarbeiterTel;
	}
	public void setMitarbeiterTel(String mitarbeiterTel) {
		this.mitarbeiterTel = mitarbeiterTel;
	}
	public String getMitarbeiterBild() {
		return mitarbeiterBild;
	}
	public void setMitarbeiterBild(String mitarbeiterBild) {
		this.mitarbeiterBild = mitarbeiterBild;
	}
	public String getMitarbeiterRolle() {
		return mitarbeiterRolle;
	}
	public void setMitarbeiterRolle(String mitarbeiterRolle) {
		this.mitarbeiterRolle = mitarbeiterRolle;
	}
	
}