package service;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Named

public class AnforderungService {
	
	dao.AnforderungDao anfDao;
	
	public static List<model.Anforderung> anforderungen = Arrays.asList(new model.Anforderung("m", "m"),
			new model.Anforderung("h1", "h2"));
	
	
	
	
	
	
	
	
	public AnforderungService() {
		
	}
	
	public List<model.Anforderung> getAnforderungen() {
		return anforderungen;
	}
	
	
	


	
	
	
	

	
	
	
}