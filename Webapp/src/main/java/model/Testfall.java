package model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@Named
public class Testfall implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer testfallId;
	private String testfallNr;
	private String testfallName;
	private String testfallBeschreibung;
	private String testfallZiel;
	private String testfallErgebnis;
	/*
	 * private List<Anmerkungen> anmerkungen;
	 * private List<Voraussetzungen> voraussetzungen;
	 * private Liste<Testschritte> testschritte;
	 * */
	@ManyToOne(optional = false)
	@JoinColumn(name="ersteller_id", nullable=false)
	private Mitarbeiter testfallErsteller;
	
	@ManyToOne(optional = true)
	@JoinColumn(name="tester_id", nullable=true)
	private Mitarbeiter testfallTester;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "testfall_anforderungen", 
	joinColumns= {@JoinColumn(name="testfall_id")}, 
	inverseJoinColumns = {@JoinColumn(name="anf_id")})
	private Set<Anforderung> anforderung = new HashSet<>();
	
	
	
	public String getTestfallNr() {
		return testfallNr;
	}
	public void setTestfallNr(String testfallNr) {
		this.testfallNr = testfallNr;
	}
	public String getTestfallBeschreibung() {
		return testfallBeschreibung;
	}
	public void setTestfallBeschreibung(String testfallBeschreibung) {
		this.testfallBeschreibung = testfallBeschreibung;
	}
	public String getTestfallName() {
		return testfallName;
	}
	public void setTestfallName(String testfallName) {
		this.testfallName = testfallName;
	}
	public String getTestfallZiel() {
		return testfallZiel;
	}
	public void setTestfallZiel(String testfallZiel) {
		this.testfallZiel = testfallZiel;
	}
	public String getTestfallErgebnis() {
		return testfallErgebnis;
	}
	public void setTestfallErgebnis(String testfallErgebnis) {
		this.testfallErgebnis = testfallErgebnis;
	}
	public Mitarbeiter getTestfallErsteller() {
		return testfallErsteller;
	}
	public void setTestfallErsteller(Mitarbeiter testfallErsteller) {
		this.testfallErsteller = testfallErsteller;
	}
	public Mitarbeiter getTestfallTester() {
		return testfallTester;
	}
	public void setTestfallTester(Mitarbeiter testfallTester) {
		this.testfallTester = testfallTester;
	}
	public Set<Anforderung> getAnforderung() {
		return anforderung;
	}
	public void setAnforderung(Set<Anforderung> anforderung) {
		this.anforderung = anforderung;
	}
	
}