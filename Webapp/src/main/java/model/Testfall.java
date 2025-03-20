package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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
	private String anmerkung;
	private String erwartetesErgebnis;
	
	
	@ManyToOne
	@JoinColumn(name="ersteller") 
	private Arbeiter mitarbeiter;
	
	@OneToMany(mappedBy="test", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<model.Testschritte> testschritte = new ArrayList<>();
	
	@OneToMany(mappedBy="test", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<model.Voraussetzung> voraussetzungen = new ArrayList<>();
	
	

	public Testfall() {}
	
	public Testfall(String nr, String bezeichnung, String ziel, String beschreibung, List<model.Testschritte> schritte) {
		this.nr = nr;
		this.bezeichnung = bezeichnung;
		this.ziel = ziel;
		this.beschreibung = beschreibung;
		this.testschritte = schritte;
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
	
	
	public List<model.Testschritte> getTestschritte() {
		return testschritte;
	}
	
	public void setTestschritte(List<model.Testschritte> schritte) {
		this.testschritte = schritte;
	}

	public String getAnmerkung() {
		return anmerkung;
	}

	public void setAnmerkung(String anmerkung) {
		this.anmerkung = anmerkung;
	}
	
	public String getErwartetesErgebnis() {
		return erwartetesErgebnis;
	}

	public void setErwartetesErgebnis(String erwartetesErgebnis) {
		this.erwartetesErgebnis = erwartetesErgebnis;
	}
	

	public List<model.Voraussetzung> getVoraussetzungen() {
		return voraussetzungen;
	}
	
	public void setVoraussetzungen(List<model.Voraussetzung> voraussetzungen) {
		this.voraussetzungen = voraussetzungen;
	}
}