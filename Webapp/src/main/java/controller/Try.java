package controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


@Named("shop")
@ApplicationScoped
public class Try {
	private List<model.Anforderung> anfListe = new ArrayList<model.Anforderung>();
	
	public Try() {
		anfListe.add(new model.Anforderung("2", "2"));
	}
	
	public List<model.Anforderung> getAnfListe() {
		return anfListe;
	}
}