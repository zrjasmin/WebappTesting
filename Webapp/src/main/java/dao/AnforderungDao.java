package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	
	//bestimmte Anforderung finden
	public model.Anforderung findAnf(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Anforderung anf = em.find(model.Anforderung.class, id);
		em.close();
		return anf;
	}

	
	
	/*speichert Anforderung und deren Ersteller*/
	public void saveAnf(long mitarbeiterID, model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		
		try {
			//Mitarbeiter festlegen
			anf.setErsteller(em.find(model.Mitarbeiter.class, mitarbeiterID));
			
			if(anf.getAnfId() == null || em.find(model.Anforderung.class, anf.getAnfId()) == null) {
				em.persist(anf);
			} else {
				em.merge(anf);
			}
			
			for(model.Akzeptanzkriterium kriterium : anf.getAnfKriterien()) {
				kriterium.setAnforderung(anf);
				model.Akzeptanzkriterium bestehendesKriterium; 
				
				if(kriterium.getId() == null ||  em.find(model.Akzeptanzkriterium.class, kriterium.getId()) == null) {
					addKriterium(kriterium, anf);
				} else {
					bestehendesKriterium = em.find(model.Akzeptanzkriterium.class, kriterium.getId());
					updateKriterium(bestehendesKriterium, kriterium);
				}
			}
			em.getTransaction().commit();
				
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
	        e.printStackTrace();
		}
	
		
		em.close();
	}
	

	public model.Anforderung updateAnf(model.Anforderung anf) {
		System.out.println("wird updaten in der Datenbank");
		EntityManager em = JpaUtil.getEntityManager();
		model.Anforderung anfToUpdate = null; 
		
		
		try {
			em.getTransaction().begin();
			anfToUpdate =  findAnf(anf.getAnfId());
			if (anfToUpdate == null) {
	            throw new IllegalArgumentException("Anforderung nicht gefunden: " + anf.getAnfId());
	        }
			
			anfToUpdate.setAnfNr(anf.getAnfNr());
			anfToUpdate.setAnfBezeichnung(anf.getAnfBezeichnung());
			anfToUpdate.setAnfBeschreibung(anf.getAnfBeschreibung());
			anfToUpdate.setAnfRisiko(anf.getAnfRisiko());
			
			List<model.Akzeptanzkriterium> kriterien = new ArrayList<>();
			for(model.Akzeptanzkriterium kriterium : anf.getAnfKriterien()) {
				model.Akzeptanzkriterium bestehendesKriterium = em.find(model.Akzeptanzkriterium.class, kriterium.getId());
				
				if(bestehendesKriterium != null) {
					System.out.println("eine bearbeites Kriterium");
					updateKriterium(bestehendesKriterium, kriterium);
					kriterien.add(bestehendesKriterium);
				} else {
					System.out.println("eine neues Kriterium");
					addKriterium(kriterium, anf);
					kriterien.add(kriterium);
				}
			}
			
			anfToUpdate.setAnfKriterien(kriterien);
			em.merge(anfToUpdate);
			em.getTransaction().commit();
			
			
			
		} catch (Exception  e) {
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
			e.printStackTrace(); 
		}
		em.close();
		return anfToUpdate;
		
	
		}
	
	public void addKriterium(model.Akzeptanzkriterium kriterium, model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
			kriterium.setAnforderung(anf);
			em.persist(kriterium);
		em.getTransaction().commit();
		em.close();
		System.out.println("kriterium wurde in DB gepspeichert");
	}

	public model.Akzeptanzkriterium updateKriterium(model.Akzeptanzkriterium altKriterium, model.Akzeptanzkriterium neuKriterium) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Akzeptanzkriterium kriteriumToUpdate = em.find(model.Akzeptanzkriterium.class, altKriterium.getId());
		if(kriteriumToUpdate != null) {
			em.getTransaction().begin();
			kriteriumToUpdate = neuKriterium;
			em.merge(kriteriumToUpdate);
			em.getTransaction().commit();
			em.close();
		}
		
		return kriteriumToUpdate;
	}
	
	public void deleteKriteriumFromAnf(model.Akzeptanzkriterium kriterium, model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
			anf = em.merge(anf);
			kriterium = em.merge(kriterium);
		em.getTransaction().commit();
		
		anf.getAnfKriterien().remove(kriterium);
		
		em.getTransaction().begin();
			em.merge(anf);
			em.remove(kriterium);
		em.getTransaction().commit();
		em.close();
		
	}
	

	public model.Anforderung getAnfAtIndex(int pos) {
		EntityManager em = JpaUtil.getEntityManager();
		CriteriaQuery<model.Anforderung> cq = criteriaBuilder.createQuery(model.Anforderung.class);
		Root<model.Anforderung> variableRoot = cq.from(model.Anforderung.class);
		em.close();
		return em.createQuery(cq).setMaxResults(1).setFirstResult(pos).getSingleResult();
	}
	
	public Integer getNextId() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNativeQuery(
				"SELECT next_val FROM 'anforderung_seq'");
		return ((Integer) query.getSingleResult()).intValue();
	}
	
	
}