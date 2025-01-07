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
	private int akzeptanzID;
	private String akzeptanzBeschr;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="anfId")
	private Anforderung anforderung;
	
	public Akzeptanzkriterium(String beschreibung) {
		this.akzeptanzBeschr = beschreibung;
	}
	
	public long getId() {
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
	
	public void setAnfoderung(Anforderung anfId) {
		this.anforderung = anfId;
	}
	
}