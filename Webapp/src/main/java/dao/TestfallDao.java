package dao;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import model.Anforderung;

@Named
@ApplicationScoped
public class TestfallDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	JpaUtil jpaUtil;
	
	CriteriaBuilder criteriaBuilder;
	
	
	public TestfallDao() {
		try {
			EntityManager em = JpaUtil.getEntityManager();
			criteriaBuilder = em.getCriteriaBuilder();
			
			long count = getCount();
            System.err.println("Wir haben " + count+ " Testfälle.");
            
            if(count == 0) {
                System.err.println("Initialisierung der Daten.");
                EntityTransaction t = em.getTransaction();
                t.begin();
               
                for(model.Testfall test : service.TestfallService.testListe) {
                	em.persist(test);
                }
                
                t.commit();
                em.close();
                
            }
		
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
		
	}
	
	public long getCount() {
		EntityManager em = JpaUtil.getEntityManager();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(model.Anforderung.class)));
        return em.createQuery(cq).getSingleResult();
	}
	
	
	public List<model.Testfall> findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		Query abfrage = em.createQuery("select a from Testfall a", model.Testfall.class);
		List<model.Testfall> test = abfrage.getResultList();
		return test;
	}
	
	
	public void addTest(long mitarbeiterID, model.Testfall test) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		
		try {
			//Mitarbeiter festlegen
			test.setErsteller(em.find(model.Arbeiter.class, mitarbeiterID));
			
			if(test.getTestId() == null || em.find(model.Testfall.class, test.getTestId()) == null) {
				em.persist(test);
			} else {
				em.merge(test);
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
	
	public model.Testfall findTest(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Testfall test = em.find(model.Testfall.class, id);
		em.close();
		return test;
	}
	
	public String maxTestNr() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createQuery("SELECT MAX(a.nr) FROM Testfall a");
		String maxNr = (String) query.getSingleResult();
		return maxNr;
	}
	
	
	public model.Testfall getLetzteAnf() {
		EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<model.Testfall> query = em.createQuery(
            "SELECT a FROM Testfall a ORDER BY a.testId DESC", model.Testfall.class);
        query.setMaxResults(1);
        List<model.Testfall> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
	
	
	public boolean exist(Integer id) {
		boolean exist;
		try {
			if(id == null) {
				exist =false;
			} else {
				EntityManager em = JpaUtil.getEntityManager();
				em.getTransaction().begin();
				model.Testfall test = em.find(model.Testfall.class, id);
				System.out.println(test);
				exist = (test != null);
				em.getTransaction().commit();
			}
			
			
			
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
		return exist;
	}
	
	
	public model.Testfall updateTest(model.Testfall test) {
		System.out.println("wird updaten in der Datenbank");
		EntityManager em = JpaUtil.getEntityManager();
		model.Testfall testToUpdate = null; 
		
		
		try {
			em.getTransaction().begin();
			testToUpdate =  findTest(test.getTestId());
			
			
			
			testToUpdate.setBeschreibung(test.getBeschreibung());
			testToUpdate.setBezeichnung(test.getBezeichnung());

			testToUpdate.setZiel(test.getZiel());

			

			em.merge(testToUpdate);
			em.getTransaction().commit();
			
			
			
		} catch (Exception  e) {
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
			e.printStackTrace(); 
		} finally {
			em.close();
		}

		return testToUpdate;
		
	
		}
	
	
	public void deleteTest(model.Testfall test) {
		EntityManager em = JpaUtil.getEntityManager();
		
		System.out.println("Test löschen: " + test.getTestId());
		
		try {
			em.getTransaction().begin();
			
			model.Testfall deleteTest = em.find(model.Testfall.class, test.getTestId());
			if(deleteTest != null) {
				em.remove(deleteTest);
			} 
			em.getTransaction().commit();
			
			//Verfikation
			
			
		} catch(Exception e) {
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
	        e.printStackTrace();
		}
		finally {
			em.close();
		}
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	
}