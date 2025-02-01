package model;

import java.io.Serializable;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Named
public class Testschritt implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer testschrittId;
	private String testschrittBesch;
	private Testfall testfall;
	
	
	public String getTestschrittBesch() {
		return testschrittBesch;
	}
	public void setTestschrittBesch(String testschrittBesch) {
		this.testschrittBesch = testschrittBesch;
	}
	
	public Testfall getTestfallId() {
		return testfall;
	}
	public void setTestfall(Testfall testfall) {
		this.testfall = testfall;
	}
	

}