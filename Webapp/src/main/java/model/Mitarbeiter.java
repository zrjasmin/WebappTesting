package model;

import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Entity
@Named
@RequestScoped
public class Mitarbeiter {
	
	public enum Rolle {
		TESTER, 
		RE,
		TESTERSTELLER,
		TESTMANAGER
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long mitarbeiterId;
	private String mitarbeiterVorn;
	private String mitarbeiterNach;
	private String mitarbeiterEmail;
	private String mitarbeiterUrl;
	private Rolle mitarbeiterRolle;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "mitarbeiter_id")
	private List<Anforderung> erstellteAnf;
	
	
	public Mitarbeiter() {}
	
	public Mitarbeiter(String Vorname, String Nachname, String Email, String BildUrl, Rolle rolle) {
		this.mitarbeiterVorn = Vorname;
		this.mitarbeiterNach = Nachname;
		this.mitarbeiterEmail = Email;
		this.mitarbeiterUrl = BildUrl;
		this.mitarbeiterRolle = rolle;
	}
	
	public Mitarbeiter(String Vorname, String Nachname, String Email, String BildUrl, Rolle rolle, model.Anforderung erstelltAnf) {
		this.mitarbeiterVorn = Vorname;
		this.mitarbeiterNach = Nachname;
		this.mitarbeiterEmail = Email;
		this.mitarbeiterUrl = BildUrl;
		this.mitarbeiterRolle = rolle;
	}
	
	public long getMitarbeiterId() {
		return mitarbeiterId;
	}

	public String getMitarbeiterVorn() {
		return mitarbeiterVorn;
	}

	public void setMitarbeiterVorn(String mitarbeiterVorn) {
		this.mitarbeiterVorn = mitarbeiterVorn;
	}

	public String getMitarbeiterNach() {
		return mitarbeiterNach;
	}

	public void setMitarbeiterNach(String mitarbeiterNach) {
		this.mitarbeiterNach = mitarbeiterNach;
	}

	public String getMitarbeiterEmail() {
		return mitarbeiterEmail;
	}

	public void setMitarbeiterEmail(String mitarbeiterEmail) {
		this.mitarbeiterEmail = mitarbeiterEmail;
	}

	public String getMitarbeiterUrl() {
		return mitarbeiterUrl;
	}

	public void setMitarbeiterUrl(String mitarbeiterUrl) {
		this.mitarbeiterUrl = mitarbeiterUrl;
	}

	public Rolle getMitarbeiterRolle() {
		return mitarbeiterRolle;
	}

	public void setMitarbeiterRolle(Rolle mitarbeiterRolle) {
		this.mitarbeiterRolle = mitarbeiterRolle;
	}
}