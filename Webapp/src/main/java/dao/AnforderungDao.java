package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.processing.Find;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Anforderung;
import service.AnforderungService;

@Named
@ApplicationScoped
public class AnforderungDao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	JpaUtil jpaUtil;
	
	CriteriaBuilder criteriaBuilder;
	
	public AnforderungDao() {
		try {
			EntityManager em = JpaUtil.getEntityManager();
			criteriaBuilder = em.getCriteriaBuilder();
			
			long count = getAnfCount();
            System.err.println("Wir haben " + count+ " Artikeln.");
            
            if(count == 0) {
                System.err.println("Initialisierung der Daten.");
                EntityTransaction t = em.getTransaction();
                t.begin();
               
                for(model.Anforderung anforderung : service.AnforderungService.anfListe) {
                	em.persist(anforderung);
                }
                
                t.commit();
                em.close();
                
            }
		
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
		
	}
	
	public long getAnfCount() {
		EntityManager em = JpaUtil.getEntityManager();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(model.Anforderung.class)));
        return em.createQuery(cq).getSingleResult();
	}
	
	
	/*alle Anforderungen auslesen*/
	public List<model.Anforderung> findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		Query abfrage = em.createQuery("select a from Anforderung a", model.Anforderung.class);
		List<model.Anforderung> anforderungen = abfrage.getResultList();
		return anforderungen;
	}

	/*speichert Anforderung und deren Ersteller*/
	public void saveAnf(long mitarbeiterID, model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		anf.setErsteller(em.find(model.Mitarbeiter.class, mitarbeiterID));
		em.persist(anf);
		
		/*Kriterien zur passenden Anforderung speichern*/
		for(model.Akzeptanzkriterium kriterium : anf.getAnfKriterien()) {
			kriterium.setAnforderung(anf);
			em.persist(kriterium);
		}
		em.getTransaction().commit();
		em.close();
	}
	

	public void addMitarbeiter(model.Anforderung anforderung, model.Mitarbeiter erstellerID) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		model.Mitarbeiter ersteller = em.find(model.Mitarbeiter.class, erstellerID);
		
		anforderung.setErsteller(ersteller);
		ersteller.getErstellteAnf().add(anforderung);
		em.persist(anforderung);
		em.getTransaction().commit();
		em.close();
				
	}
	
	
	public model.Anforderung findAnf(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Anforderung anf = em.find(model.Anforderung.class, id);
		em.close();
		return anf;
	}
	
	public model.Anforderung updateAnf(model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Anforderung anfToUpdate = findAnf(anf.getAnfId());
		
		em.getTransaction().begin();
		anfToUpdate.setAnfNr(anf.getAnfNr());
		anfToUpdate.setAnfBezeichnung(anf.getAnfBezeichnung());
		anfToUpdate.setAnfBeschreibung(anf.getAnfBeschreibung());
		anfToUpdate.setAnfRisiko(anf.getAnfRisiko());
		anfToUpdate.setAnfKriterien(anf.getAnfKriterien());
		em.merge(anfToUpdate);
		em.getTransaction().commit();
		em.close();
		return anfToUpdate;
		}






	public model.Anforderung getAnfAtIndex(int pos) {
		EntityManager em = JpaUtil.getEntityManager();
		CriteriaQuery<model.Anforderung> cq = criteriaBuilder.createQuery(model.Anforderung.class);
		Root<model.Anforderung> variableRoot = cq.from(model.Anforderung.class);
		em.close();
		return em.createQuery(cq).setMaxResults(1).setFirstResult(pos).getSingleResult();
	}
	
	public boolean exist(Integer id) {
		boolean exist;
		try {
			if(id == null) {
				exist =false;
			} else {
				EntityManager em = JpaUtil.getEntityManager();
				em.getTransaction().begin();
				model.Anforderung anf = em.find(Anforderung.class, id);
				System.out.println(anf);
				exist = (anf != null);
				em.getTransaction().commit();
			}
			
			
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
		return exist;
	}
	
	
	

	
	
	
}