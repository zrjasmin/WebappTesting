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
	
	public Akzeptanzkriterium() {
		
	}
	

	public Akzeptanzkriterium(String beschreibung) {
		this.akzeptanzBeschr = beschreibung;
	}
	
	public int getId() {
		return akzeptanzID;
	}
	
	public String getAkzeptanzBeschr() {
		return akzeptanzBeschr;
	}
	public void setAkzeptanzBeschr(String akzeptanzBeschr) {
		this.akzeptanzBeschr = akzeptanzBeschr;
	}
	


	
}