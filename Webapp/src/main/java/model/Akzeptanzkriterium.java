package model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Entity
@Named
@RequestScoped
public class Akzeptanzkriterium {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer akzeptanzID;
	private String akzeptanzBeschr;
	
	@ManyToOne
	@JoinColumn(name="anforderung_id") 
	private Anforderung anforderung;
	
	public Akzeptanzkriterium() {
		
	}
	

	public Akzeptanzkriterium(String beschreibung) {
		this.akzeptanzBeschr = beschreibung;
	}
	
	public Integer getId() {
		return akzeptanzID;
	}
	
	public String getAkzeptanzBeschr() {
		return akzeptanzBeschr;
	}
	public void setAkzeptanzBeschr(String akzeptanzBeschr) {
		this.akzeptanzBeschr = akzeptanzBeschr;
	}
	
	public Anforderung getAnforderung() {
		return anforderung;
	}
	
	public void setAnforderung(model.Anforderung anforderung) {
		this.anforderung = anforderung;	}

	
}