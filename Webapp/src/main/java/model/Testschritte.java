package model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Named
@RequestScoped
public class Testschritte {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer schrittId;
	private String	schrittBeschr;
	
	@ManyToOne
	@JoinColumn(name="test_id") 
	private model.Testfall test;
	
	
	public Testschritte() {}

	public Testschritte(String schrittBeschr) {
		this.schrittBeschr = schrittBeschr;
	}
	
	

	
	
	public Integer getId() {
		return schrittId;
	}
	
	public String getSchrittBeschr() {
		return schrittBeschr;
	}
	public void setSchrittBeschr(String schrittBeschr) {
		this.schrittBeschr = schrittBeschr;
	}
	
	public model.Testfall getTest() {
		return test;
	}
	
	public void setTest(model.Testfall test) {
		this.test = test;	}
	
}
	