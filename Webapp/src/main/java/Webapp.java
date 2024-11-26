import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class Webapp  {
	private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("webapp");
	
	public Webapp() {}
	
	
	
	
	public static void main (String []args) {
		List<Anforderung> anfListe = new ArrayList<Anforderung>();
		anfListe.add(new Anforderung(1, "Anforderung 1", "Beschreibung 1"));
		anfListe.add(new Anforderung(2, "Anforderung 2", "Beschreibung 2"));
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction t = em.getTransaction();
		t.begin();
		for(Anforderung a:anfListe) {
			em.persist(a);
		}
		t.commit();
		emf.close();
		

	}
	
	
	
	
}