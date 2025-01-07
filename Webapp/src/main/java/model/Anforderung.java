package model;

import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Entity
@Named
@RequestScoped
public class Anforderung {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long anfId;
	
	private String anfNr;
	private String anfBezeichnung;
	private String anfBeschreibung;
	private String anfZiel;
	private String anfRisiko;
	@OneToMany(mappedBy ="anforderung", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Akzeptanzkriterium> anfKriterien;
	
	
	
	
	public Anforderung() {}
	
	public Anforderung(String anfNr, String anfBezeichnung) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		
	}
	
	public Anforderung(String anfNr, String anfBezeichnung, String anfBeschreibung, String anfZiel, String anfRisiko) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		this.setAnfZiel(anfZiel);
		this.setAnfRisiko(anfRisiko);
		
		}
	
	public long getId() {
		return anfId;
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

	public List<Akzeptanzkriterium> getAnfKriterien() {
		return anfKriterien;
	}

	public void setAnfKriterien(List<Akzeptanzkriterium> anfKriterien) {
		this.anfKriterien = anfKriterien;
	}
	
	
	
	
	
	
}