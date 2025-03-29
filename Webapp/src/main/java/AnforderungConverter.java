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
@FacesConverter(value="anfConverter",  managed = true) 
public class AnforderungConverter implements Converter<model.Anforderung> {
	
	@Inject
    private service.AnforderungService anfService;
	@Inject
    private  dao.AnforderungDao anfDao = new dao.AnforderungDao();

    @Override
    public model.Anforderung getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
            	int id = Integer.parseInt(value);
            	return anfDao.findAnf(id);
                //return anfService.getAnfAsMap().get(Integer.parseInt(value));
            }
            catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid country."));
            }
        }
        else {
            return null;
        }
    }

    @Override	    
    public String getAsString(FacesContext context, UIComponent component, model.Anforderung anforderung) {
    	 if (anforderung == null) {
    	        return ""; // Never return null here!
    	    }

    	
    	if (anforderung instanceof model.Anforderung) {
    		 return String.valueOf(((model.Anforderung) anforderung).getAnfId());
           // return (anforderung != null && anforderung.getAnfId() != null ? anforderung.getAnfId().toString() : "");
        } else {
            throw new ConverterException(new FacesMessage(anforderung + " is not a valid Warehouse"));
        }
    
	
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@Inject 
	private controller.AnforderungController anfController;
	@Inject
	private dao.AnforderungDao dao;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            // Hier kannst du Logik hinzufügen, um den Arbeiter anhand der ID aus der Datenbank zu laden.
            // Zum Beispiel:
           return dao.findAnf(Integer.valueOf(value));

            // Dummy-Rückgabe für das Beispiel
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid worker."));
        }
    }
	 
	
	    @Override	    
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	      

	        if (value instanceof model.Anforderung) {
	            return String.valueOf(((model.Anforderung) value).getAnfId()); // Gibt die ID des Arbeiters zurück
	        } else {
	        	return "";
	        }
	    }*/
}
