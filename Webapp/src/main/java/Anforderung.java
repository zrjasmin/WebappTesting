import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Named
@RequestScoped
public class Anforderung implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private int anfNr;
	private String anfBezeichnung;
	private String anfBeschreibung;
	
	public Anforderung() {
	}
	public Anforderung(int Nr, String anfBezeichnung, String anfBeschreibung) {
		this.anfNr = Nr;
		this.anfBeschreibung = anfBeschreibung;
		this.anfBezeichnung = anfBezeichnung;
	}

	public int getAnfNr() {
		return anfNr;
	}

	public void setAnfNr(int anfNr) {
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
	
	
}