package model;

import java.io.Serializable;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Named
public class TestVorraussetzung implements Serializable {
	

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer VoraussetzungId;
	private String VoraussetzungBeschr;
	private Testfall testfall;
	
	public String getVoraussetzungBeschr() {
		return VoraussetzungBeschr;
	}
	public void setVoraussetzungBeschr(String voraussetzungBeschr) {
		VoraussetzungBeschr = voraussetzungBeschr;
	}
	
	public Testfall getTestfall() {
		return testfall;
	}
	public void setTestfall(Testfall testfall) {
		this.testfall = testfall;
	}
	

}