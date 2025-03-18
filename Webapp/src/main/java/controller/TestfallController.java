package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;



@Named
@SessionScoped
public class TestfallController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	service.TestfallService service;
	@Inject 
	dao.TestfallDao testDao;
	
	private Integer selectedId;
	private model.Testfall selectedTest;
	
	public TestfallController() {
		selectedTest = new model.Testfall();
		
	}
	
	public String showNr() {
		String nummer;
		if(selectedTest == null) {
			nummer = service.generateNumber();
			System.out.println("nummer "+nummer);
		} else {
			nummer = selectedTest.getNr();
		}
		
		return nummer;
	}
	
	public String selectTestfall(Integer id) {
		
		selectedTest = testDao.findTest(id);
		setSelectedId(id);
		System.out.println("ausgewählte Anforderung: " + selectedTest.getTestId());

		return "detailTestfall?faces-redirect=true&id=" + selectedTest.getTestId();
			
	}

	public Integer getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(Integer selectedId) {
		this.selectedId = selectedId;
	}
	
	
	public String deleteTest() {
		testDao.deleteTest(selectedTest);
		System.out.println("ID zum löschen: " + selectedTest.getTestId());
		return "/testfaelle.xhtml?faces-redirect=true";
	}
	
	
	public String neuerTest() {
		selectedTest = new model.Testfall();
		selectedTest.setNr(service.generateNumber());
	
		System.out.println(selectedTest.getTestId());
		return "editTestfall.xhtml?faces-redirect=true";
	}
}