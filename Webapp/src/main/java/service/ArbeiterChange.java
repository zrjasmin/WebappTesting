package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.AnforderungDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import model.Anforderung;

import java.io.Serializable;

@Named
@RequestScoped
public class ArbeiterChange implements Serializable{


	private static final long serialVersionUID = 1L;

	public ArbeiterChange() {
		
	}
	
	
}