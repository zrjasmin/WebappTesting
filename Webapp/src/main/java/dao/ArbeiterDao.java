package dao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import model.Arbeiter;

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
	
	
	public Map<Integer, model.Arbeiter> alleArbeiter() {
		EntityManager em = JpaUtil.getEntityManager();
        HashMap<Integer, Arbeiter> arbeiterMap = new HashMap<>();
		List<model.Arbeiter> abfrage = em.createQuery("select a from Arbeiter a", model.Arbeiter.class).getResultList();
		
		for(model.Arbeiter ar : abfrage) {
			arbeiterMap.put(ar.getArbeiterId(), ar);
		}
		
		return arbeiterMap;
	}
	
	public model.Arbeiter findArbeiter(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Arbeiter arbeiter = em.find(model.Arbeiter.class, id);
		em.close();
		return arbeiter;
	}
	
/*	public List<model.Rolle> alleRollen() {
		EntityManager em = JpaUtil.getEntityManager();
		Query abfrage = em.createQuery("select a from Rolle a", model.Rolle.class);
		List<model.Rolle> rollen = abfrage.getResultList();
		return rollen;
	}
	*/
	
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
	
	/*public void saveRolle(model.Rolle rolle) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			
			if(rolle.getId() == null || em.find(model.Rolle.class, rolle.getId()) == null ) {
				em.persist(rolle);
			} else {
				em.merge(rolle);
			}
			em.getTransaction().commit();
			
		} catch(Exception e) {
			
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
	        e.printStackTrace();
		}
		em.close();
		
	}*/
	
/*	public List<String> getBerechtigungen(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
	
		 try {
		        Rolle rolle = em.find(Rolle.class, id);

		        if (rolle != null) {
		            return rolle.getBerechtigungen(); // Gibt die Liste der Berechtigungen zurück
		        } else {
		            System.out.println("Die angegebene Rolle existiert nicht.");
		            return null;
		        }

		    } finally {
		        em.close(); // Stelle sicher, dass der EntityManager immer geschlossen wird
		    }
	}*/
	
/*	public boolean checkBerechtigung(model.Arbeiter arbeiter, String taetigkeit) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		boolean check =false;
		try {
			System.out.println(arbeiter.getRolle());
			model.Rolle rolle = arbeiter.getRolle();
			for(String b : rolle.getBerechtigungen()) {
				System.out.print(b);
						}
			if(rolle != null)  {
				rolle = em.find(model.Rolle.class, arbeiter.getRolle().getId());
			}
			 
			 if (rolle != null && rolle.getBerechtigungen().contains(taetigkeit)) {
	                check = true;
	            }
			
		}  catch(Exception e) {
			
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
	        e.printStackTrace();
		}
		em.close();
		return check;		
	}*/
}