package model;



import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Named
@RequestScoped
public class Akzeptanzkriterien {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int akzeptanzID;
	private String akzeptanzBeschreibung;
	
	public Akzeptanzkriterien() {
		
	}

	public Akzeptanzkriterien(String akzeptanzBeschreibung) {
		this.akzeptanzBeschreibung = akzeptanzBeschreibung;
	}
	
	
	
	public String getAkzeptanzBeschreibung() {
		return akzeptanzBeschreibung;
	}

	public void setAkzeptanzBeschreibung(String akzeptanzBeschreibung) {
		this.akzeptanzBeschreibung = akzeptanzBeschreibung;
	}

	
	
}