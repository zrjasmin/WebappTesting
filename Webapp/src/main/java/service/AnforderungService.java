package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.AnforderungDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import model.Anforderung;

import java.io.Serializable;

@Named
@ViewScoped
public class AnforderungService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	controller.Try shop;
	
	
	
	
	public model.Anforderung getAnforderung(int index) {
		return shop.getAnfListe().get(index);
	}
	
	public void addAnf(String i, String j) {
		shop.getAnfListe().add(new Anforderung(i, j));
	}
	
	
	
	

	
	
}