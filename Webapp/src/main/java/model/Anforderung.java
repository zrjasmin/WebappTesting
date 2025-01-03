package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Named
@RequestScoped
public class Anforderung {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int anfId;
	private String anfNr;
	private String anfBezeichnung;
	private String anfBeschreibung;
	private String anfZiel;

	private String anfRisiko;
	
	private Mitarbeiter erstelltVon;
	@OneToMany
	private List<Akzeptanzkriterien> anfAkzeptanz;
	
	
	
	public Anforderung() {}
	
	public Anforderung(String anfNr, String anfBezeichnung, String anfBeschreibung) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		
	}
	
	public Anforderung(String anfNr, String anfBezeichnung, String anfBeschreibung, String anfZiel, String anfRisiko,List<Akzeptanzkriterien> anfKriterien) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		this.setAnfZiel(anfZiel);
		this.setAnfRisiko(anfRisiko);
		this.anfAkzeptanz = new ArrayList<>(anfKriterien);
		
		}

	public String getAnfNr() {
		return anfNr;
	}

	public void setAnfNr(String anfNr) {
		this.anfNr = anfNr;
	}

	public String getAnfBezeichnung() {
		return anfBezeichnung;
	}

	public void setAnfBezeichnung(String anfBezeichnung) {
		this.anfBezeichnung = anfBezeichnung;
	}

	public String getAnfBeschreibung() {
		return anfBeschreibung;
	}

	public void setAnfBeschreibung(String anfBeschreibung) {
		this.anfBeschreibung = anfBeschreibung;
	}

	public String getAnfZiel() {
		return anfZiel;
	}

	public void setAnfZiel(String anfZiel) {
		this.anfZiel = anfZiel;
	}

	public String getAnfRisiko() {
		return anfRisiko;
	}

	public void setAnfRisiko(String anfRisiko) {
		this.anfRisiko = anfRisiko;
	}
	
	public void addAkzeptanzkriterium(Akzeptanzkriterien kriterium) {
		anfAkzeptanz.add(kriterium);
    }

    public List<Akzeptanzkriterien> getAkzeptanzkriterien() {
        return anfAkzeptanz;
    }

	public Mitarbeiter getErstelltVon() {
		return erstelltVon;
	}

	public void setErstelltVon(Mitarbeiter erstelltVon) {
		this.erstelltVon = erstelltVon;
	}
	
	
	
	
	
	
}