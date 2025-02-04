package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class TestfallService implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private model.Testfall test = new model.Testfall();
	
	@Inject
	private dao.TestfallDao testDao;
	
	@Inject 
	private service.MitarbeiterService arbeiterService;
/*
	@Inject 
	private controller.Test controller;
	*/

	public static final List<model.Testfall> testListe = new ArrayList<>();
	
	
	public TestfallService() {
		testDao = new dao.TestfallDao();
		arbeiterService = new service.MitarbeiterService();
		
		model.Testfall test1 = new model.Testfall("erster Testfall");
		testDao.saveTest(2, test1);
	}
	
	public List<model.Testfall> getTestListe() {
		return testDao.findAll();
	}
	

}