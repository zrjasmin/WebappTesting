package controller;
import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AnforderungController implements Serializable {
	
	public void comment() {
		System.out.println("Hallo");
	}
}