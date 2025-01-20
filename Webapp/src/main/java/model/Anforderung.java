package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Entity
@Named
public class Anforderung implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer anfId;
	
	private String anfNr;
	private String anfBezeichnung;
	private String anfBeschreibung;
	private String anfZiel;
	private String anfRisiko;
	
	@OneToMany(mappedBy="anforderung", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<model.Akzeptanzkriterium> anfKriterien = new ArrayList<>();
	
	@ManyToOne(optional = false)
	@JoinColumn(name="ersteller_id", nullable=false)
	private Mitarbeiter ersteller;
	
	
	
	public Anforderung() {}
	
	public Anforderung(String anfNr, String anfBezeichnung,String anfBeschreibung, String anfZiel, String anfRisiko) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		this.anfZiel = anfZiel;
		this.anfRisiko = anfRisiko;
		
	}
	
	public Anforderung(String anfNr, String anfBezeichnung, String anfBeschreibung, String anfZiel, String anfRisiko, List<Akzeptanzkriterium> anfKriterien) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		this.anfZiel = anfZiel;
		this.anfRisiko = anfRisiko;
		this.anfKriterien = anfKriterien;

		
		}
	
	public Integer getAnfId() {
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
	
	public Mitarbeiter getErsteller() {
		return ersteller;
	}
	
	public void setErsteller(Mitarbeiter ersteller) {
		this.ersteller = ersteller;
	}
	
	
	
	
	
	
}