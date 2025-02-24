import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;


import jakarta.inject.Inject;

@Named
@ApplicationScoped
@FacesConverter("arbeiterConverter") 
public class ArbeiterConverter implements Converter {
	
	@Inject 
	private controller.ArbeiterController arbeiterController;
	@Inject
	private dao.ArbeiterDao dao;
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            // Hier kannst du Logik hinzufügen, um den Arbeiter anhand der ID aus der Datenbank zu laden.
            // Zum Beispiel:
           return dao.findArbeiter(Integer.valueOf(value));

            // Dummy-Rückgabe für das Beispiel
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid worker."));
        }
    }
	 
	
	    @Override	    
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	      

	        if (value instanceof model.Arbeiter) {
	            return String.valueOf(((model.Arbeiter) value).getArbeiterId()); // Gibt die ID des Arbeiters zurück
	        } else {
	        	return "";
	        }
	    }
}
