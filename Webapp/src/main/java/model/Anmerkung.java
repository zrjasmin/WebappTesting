package model;

import java.io.Serializable;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Named
public class Anmerkung implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer AnmerkungId;
	private String anmerkungBesch;
	private Testfall testfall;
	
	public String getAnmerkungBesch() {
		return anmerkungBesch;
	}
	public void setAnmerkungBesch(String anmerkungBesch) {
		this.anmerkungBesch = anmerkungBesch;
	}

	public Testfall getTestfall() {
		return testfall;
	}
	public void setTestfall(Testfall testfall) {
		this.testfall = testfall;
	}

}