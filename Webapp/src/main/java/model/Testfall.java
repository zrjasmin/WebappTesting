package model;

import java.io.Serializable;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Named
public class Testfall implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer testId;
	
	private String nr;
	private String bezeichnung;
	private String ziel;
	private String beschreibung;
	
	
	@ManyToOne
	@JoinColumn(name="ersteller") 
	private Arbeiter mitarbeiter;

	public Testfall() {}
	
	public Testfall(String nr, String bezeichnung, String ziel, String beschreibung) {
		this.nr = nr;
		this.bezeichnung = bezeichnung;
		this.ziel = ziel;
		this.beschreibung = beschreibung;
	}
	
	
	public Integer getTestId() {
		return testId;
	}
	
	public String getNr() {
		return nr;
	}


	public void setNr(String nr) {
		this.nr = nr;
	}


	public String getZiel() {
		return ziel;
	}


	public void setZiel(String ziel) {
		this.ziel = ziel;
	}


	public String getBezeichnung() {
		return bezeichnung;
	}


	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}


	public String getBeschreibung() {
		return beschreibung;
	}


	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	public model.Arbeiter getErsteller() {
		return mitarbeiter;
	}
	
	public void setErsteller(model.Arbeiter ersteller) {
		this.mitarbeiter = ersteller;
	}
	
}