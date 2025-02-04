package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;

@Named
@ApplicationScoped
public class TestfallDao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	JpaUtil jpaUtil;
	
	CriteriaBuilder criteriaBuilder;
	
	public List<model.Testfall> findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		Query abfrage = em.createQuery("select a from Anforderung a", model.Anforderung.class);
		List<model.Testfall> testfälle = abfrage.getResultList();
		return testfälle;
	}
	
	public void saveTest(long mitarbeiterID, model.Testfall testfall) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		testfall.setTestfallErsteller(em.find(model.Mitarbeiter.class, mitarbeiterID));
		em.persist(testfall);
		
		/*Kriterien zur passenden Anforderung speichern*/
		/*
		for(model.Testschritt schritte : testfall.getTestschritte() ) {
			schritte.setTestfall(testfall);
			em.persist(schritte);
		}*/
		em.getTransaction().commit();
		em.close();
	}
	
	
	
}