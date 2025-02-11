package controller;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class DetailController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	service.AnforderungService service;
	@Inject 
	dao.AnforderungDao anfDao;
	@Inject
	AnforderungController anfController;
	
	private Integer selectedId;
	private model.Anforderung anforderung;
	
	
	public void init() {
        if (selectedId != null) {
            anforderung = anfDao.findAnf(selectedId);
        }
      
    }
	
	public DetailController() {

	}
	
	public Integer getSelectedId() {
		return selectedId;
	}
	
	public void setSelectedId(Integer id) {
		this.selectedId = id;
		init();
		
	}
	
	public model.Anforderung getAnforderung() {
		return anforderung;
	}
	
	
	
}