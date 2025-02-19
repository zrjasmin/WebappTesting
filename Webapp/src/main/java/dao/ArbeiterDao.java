package dao;
import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import model.Arbeiter;
import service.ArbeiterChange;

@Named
@ApplicationScoped
public class ArbeiterDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	JpaUtil jpaUtil;
	
	CriteriaBuilder criteriaBuilder;
	
	public ArbeiterDao( ) {
		try {
			EntityManager em = JpaUtil.getEntityManager();
			criteriaBuilder = em.getCriteriaBuilder();
			
			long count = getArbeiterAnzahl();
            System.err.println("Wir haben " + count+ " Arbeiter.");
            
            if(count == 0) {
                System.err.println("Initialisierung der Arbeiter.");
                EntityTransaction t = em.getTransaction();
                t.begin();
               
                for(model.Arbeiter arbeiter : service.ArbeiterService.arbeiterListe) {
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
	
	
	public long getArbeiterAnzahl() {
		EntityManager em = JpaUtil.getEntityManager();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(model.Arbeiter.class)));
        return em.createQuery(cq).getSingleResult();
	}
	
	public List<model.Arbeiter> alleArbeiter() {
		EntityManager em = JpaUtil.getEntityManager();
		Query abfrage = em.createQuery("select a from Arbeiter a", model.Arbeiter.class);
		List<model.Arbeiter> arbeiter = abfrage.getResultList();
		return arbeiter;
	}
	
	
	public void createArbeiter(model.Arbeiter arbeiter) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			
			if(arbeiter.getArbeiterId() == null || em.find(model.Arbeiter.class, arbeiter.getArbeiterId()) == null ) {
				em.persist(arbeiter);
			} else {
				em.merge(arbeiter);
			}
			em.getTransaction().commit();
			
		} catch(Exception e) {
			
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
	        e.printStackTrace();
		}
		em.close();
		
		
	
		
	}
}