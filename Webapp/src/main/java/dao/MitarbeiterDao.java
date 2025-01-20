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
public class MitarbeiterDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CriteriaBuilder criteriaBuilder;
	
	public MitarbeiterDao() {
		try {
			EntityManager em = JpaUtil.getEntityManager();
			criteriaBuilder = em.getCriteriaBuilder();
			
			long count = getArbeiterCount();
            System.err.println("Wir haben " + count+ " Mitarbeiter.");
            
            if(count == 0) {
                System.err.println("Initialisierung der Daten.");
                EntityTransaction t = em.getTransaction();
                t.begin();
               
                for(model.Mitarbeiter arbeiter : service.MitarbeiterService.arbeiterListe) {
                	em.persist(arbeiter);
                }
                
                t.commit();
                em.close();
                
            }
		
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
		}
		
	}
	
	public long getArbeiterCount() {
		EntityManager em = JpaUtil.getEntityManager();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(model.Mitarbeiter.class)));
        return em.createQuery(cq).getSingleResult();
	}
	

	public List<model.Mitarbeiter> alleMitarbeiter() {
		List<model.Mitarbeiter> mitarbeiter = new ArrayList<>();
		EntityManager em = JpaUtil.getEntityManager();
		Query abfrage = em.createQuery("select a from Mitarbeiter a", model.Mitarbeiter.class);
		mitarbeiter = abfrage.getResultList();
		return mitarbeiter;
	}
	
	public void saveMitarbeiter(model.Mitarbeiter mitarbeiter) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(mitarbeiter);
		em.getTransaction().commit();
		em.close();
	}
	
	public void saveAllMitarbeiter(List<model.Mitarbeiter> mitarbeiter) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		
		for(model.Mitarbeiter arbeiter : mitarbeiter) {
			
			if(arbeiter.getMitarbeiterId() == null) {
				em.persist(arbeiter);
			} else {
				em.merge(arbeiter);
			}
			
		}
		em.getTransaction().commit();
		em.close();
	}
	/*
	public List<model.Mitarbeiter.Rolle> alleRollen() {
		List<model.Mitarbeiter> rollen = new ArrayList<>();
		EntityManager em = JpaUtil.getEntityManager();
		Query abfrage = em.createQuery("select a from Mitarbeiter a", model.Mitarbeiter.class);
		rellen = abfrage.getResultList();
		return rollen;
	}
	*/
	
	
	
	
}
