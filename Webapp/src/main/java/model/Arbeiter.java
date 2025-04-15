package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;




@Entity
@Named
public class Arbeiter implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer arbeiterId;
	private String vorname;
	private String nachname;
	private String email;
	private String bildUrl;

	
	
	
	@OneToMany(mappedBy="mitarbeiter", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<model.Anforderung> anforderungen = new ArrayList<>();
	
	@OneToMany(mappedBy="mitarbeiter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<model.Testfall> testfall = new ArrayList<>();
	
	@ManyToOne
    @JoinColumn(name = "rolle_id", nullable = false)
    private Rolle rolle;

	public Arbeiter() {
		
	}
	
	public Arbeiter(String vorname, String nachname, String email, String url) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		this.bildUrl = url;

	}
	
	public Integer getArbeiterId() {
		return arbeiterId;
	}
	
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBildUrl() {
		return bildUrl;
	}

	public void setBildUrl(String bildUrl) {
		this.bildUrl = bildUrl;
	}
	
	public model.Rolle getRolle() {
		return rolle;
	}
	
	public void setRolle(model.Rolle rolle) {
		this.rolle = rolle;
	}
	


	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof model.Arbeiter)) return false;

	    model.Arbeiter other = (model.Arbeiter) obj;

	    return this.arbeiterId == other.arbeiterId; // Vergleich nach ID
	}
	
}