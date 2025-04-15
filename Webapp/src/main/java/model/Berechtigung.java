package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
@Named
public class Berechtigung implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String beschreibung;
	
	@ManyToMany(mappedBy = "berechtigungen")
	private Set<Rolle> rollen = new HashSet<>();
	

	public Berechtigung() {}
	
	public Berechtigung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
	public Integer getId() {
		return id;
	}
	
	
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	} 
	
	public Set<model.Rolle> getRollen() {
		return rollen;
	}
	
	public void setRollen(Set<model.Rolle> rollen) {
		this.rollen = rollen;
	}
	

}
	