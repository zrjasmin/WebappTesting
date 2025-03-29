package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Entity
@Named
public class Anforderung implements Serializable,  Comparable<model.Anforderung> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer anfId;
	
	private String anfNr;
	private String anfBezeichnung;
	private String anfBeschreibung;
	private String anfZiel;
	private String anfRisiko;
	
	@OneToMany(mappedBy="anforderung", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<model.Akzeptanzkriterium> anfKriterien = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="ersteller") 
	private Arbeiter mitarbeiter;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "anforderung_verweis",
        joinColumns = @JoinColumn(name = "anf_id"),
        inverseJoinColumns = @JoinColumn(name = "verknüpfte_anf_id")
    )
    private List<model.Anforderung> verknüpfteAnforderungen = new ArrayList<>();

	@OneToOne(mappedBy = "anf")
    private model.Testfall testfall;
	
	
	public Anforderung() {}
	
	public Anforderung(String anfNr, String anfBezeichnung,String anfBeschreibung, String anfZiel, String anfRisiko) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		this.anfZiel = anfZiel;
		this.anfRisiko = anfRisiko;
		
	}
	
	public Anforderung(String anfNr, String anfBezeichnung, String anfBeschreibung, String anfZiel, String anfRisiko, List<Akzeptanzkriterium> anfKriterien, List<model.Anforderung> verknüpfteAnf) {
		this.anfNr = anfNr;
		this.anfBezeichnung = anfBezeichnung;
		this.anfBeschreibung = anfBeschreibung;
		this.anfZiel = anfZiel;
		this.anfRisiko = anfRisiko;
		this.anfKriterien = anfKriterien;
		this.verknüpfteAnforderungen = verknüpfteAnf;

		
		}
	
	public Integer getAnfId() {
		return anfId;
	}

	public String getAnfNr() {
		return anfNr;
	}

	public void setAnfNr(String anfNr) {
		this.anfNr = anfNr;
	}

	public String getAnfBezeichnung() {
		return anfBezeichnung;
	}

	public void setAnfBezeichnung(String anfBezeichnung) {
		this.anfBezeichnung = anfBezeichnung;
	}

	public String getAnfBeschreibung() {
		return anfBeschreibung;
	}

	public void setAnfBeschreibung(String anfBeschreibung) {
		this.anfBeschreibung = anfBeschreibung;
	}

	public String getAnfZiel() {
		return anfZiel;
	}

	public void setAnfZiel(String anfZiel) {
		this.anfZiel = anfZiel;
	}

	public String getAnfRisiko() {
		return anfRisiko;
	}

	public void setAnfRisiko(String anfRisiko) {
		this.anfRisiko = anfRisiko;
	}

	public List<Akzeptanzkriterium> getAnfKriterien() {
		return anfKriterien;
	}

	public void setAnfKriterien(List<Akzeptanzkriterium> anfKriterien) {
		this.anfKriterien = anfKriterien;
	}
	
	public model.Arbeiter getErsteller() {
		return mitarbeiter;
	}
	
	public void setErsteller(model.Arbeiter ersteller) {
		this.mitarbeiter = ersteller;
	}
	
	
	public List<Anforderung> getVerknüpfteAnforderungen() {
		return verknüpfteAnforderungen;
	}
	
	public void setVerknüpfteAnforderungen(List<Anforderung> anforderungen) {
		this.verknüpfteAnforderungen = anforderungen;
	}
	

	
	
	
	
	  @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        model.Anforderung anf = (model.Anforderung) o;
	        return Objects.equals(anfId, anf.anfId);               
	        		
	    }
	
	 @Override
	    public int hashCode() {
	        return Objects.hash(anfId);
	    }

	    @Override
	    public String toString() {
	        return anfBezeichnung;
	    }
	    
	    @Override
	    public int compareTo(model.Anforderung anf) {
	        return Integer.compare(this.anfId, anf.anfId);
	    }

	   
	
	
	
}