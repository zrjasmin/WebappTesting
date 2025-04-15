package dao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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
	
	
	
	
	public void saveRolle(model.Rolle rolle) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		  try {
		        // Überprüfen, ob es sich um eine neue Rolle handelt
			  if(rolle.getId() == null) {
				  Set<model.Berechtigung> berechtigungen = new HashSet<>();
				  for(model.Berechtigung be : rolle.getBerechtigungen()) {
				        
			        	if(!em.contains(be)) {
			        		be = em.merge(be);
			        	} 
			        	
			        }
				  
		        	em.persist(rolle);
		        } else {
		        	em.merge(rolle);
		        }

		  
		        em.getTransaction().commit(); // Transaktion abschließen

		     }catch (Exception e) {
		        if (em.getTransaction().isActive()) {
		            em.getTransaction().rollback(); // Rollback bei Fehlern
		        }
		        e.printStackTrace();

		    } finally {
		        em.close(); // EntityManager schließen
		    };
		
	}
	
	public model.Rolle findRolle(String rolle) {
		EntityManager em = JpaUtil.getEntityManager();
		TypedQuery<model.Rolle> q = em.createQuery("select a from Rolle a where a.name= :rolle", model.Rolle.class);
		model.Rolle rolleAbfrage = q.setParameter("rolle", rolle).getSingleResult();
		return rolleAbfrage;

	}
	
	public void saveBerechtigung(model.Berechtigung berechtigung) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			
			if(berechtigung.getId() == null || em.find(model.Berechtigung.class, berechtigung.getId()) == null ) {
				em.persist(berechtigung);
			} else {
				em.merge(berechtigung);
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
	
	public model.Berechtigung findBerechtigung(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Berechtigung b = em.find(model.Berechtigung.class, id);
		em.close();
		return b;
	}

}