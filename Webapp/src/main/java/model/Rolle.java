package model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.inject.Named;
import jakarta.persistence.*;

@Entity
@Named
public class Rolle implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	
	@OneToMany(mappedBy = "rolle", cascade = CascadeType.ALL)
    private Set<Arbeiter> arbeiter = new HashSet<>();
	
	@ManyToMany
	@JoinTable(
            name = "rolle_berechtigung",
            joinColumns = @JoinColumn(name = "rolle_id"),
            inverseJoinColumns = @JoinColumn(name = "berechtigung_id")
	)	
	private Set<Berechtigung> berechtigungen = new HashSet<>();
	
	
	public Rolle() {}
	
	public Rolle(String name) {
		this.name = name;
	}
	
	public Rolle(String name, Set<model.Berechtigung> berechtigungen) {
		this.name = name;
		this.berechtigungen = berechtigungen;
	}
	
	
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Set<Arbeiter> getArbeiter() {
		return arbeiter;
	}
	
	public void setArbeiter(Set<model.Arbeiter> arbeiter) {
		this.arbeiter = arbeiter;
	}
	
	
	public Set<model.Berechtigung> getBerechtigungen() {
		return berechtigungen;
	}
	
	public void setBerechtigungen(Set<model.Berechtigung> berechtigungen) {
		this.berechtigungen = berechtigungen;
	}
	
	public void addBerechtigung(model.Berechtigung b) {
		this.berechtigungen.add(b);
		b.getRollen().add(this);
	}
	

	public void removeBerechtigung(model.Berechtigung b) {
		this.berechtigungen.remove(b);
		b.getRollen().remove(this);
	}
	
}
