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
public class Voraussetzung {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer voraussetzungId;
	private String	voraussetzungBeschr;
	
	@ManyToOne
	@JoinColumn(name="test_id") 
	private model.Testfall test;


	
	public Voraussetzung() {}

	public Voraussetzung(String voraussetzungBeschr) {
		this.setVoraussetzungBeschr(voraussetzungBeschr);
	}
	

	public Integer getId() {
		return voraussetzungId;
	}
	
	
	
	public model.Testfall getTest() {
		return test;
	}
	
	public void setTest(model.Testfall test) {
		this.test = test;	}

	public String getVoraussetzungBeschr() {
		return voraussetzungBeschr;
	}

	public void setVoraussetzungBeschr(String voraussetzungBeschr) {
		this.voraussetzungBeschr = voraussetzungBeschr;
	}
	
}