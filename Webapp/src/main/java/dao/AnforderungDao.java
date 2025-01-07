package dao;

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

@Named
@ApplicationScoped
public class AnforderungDao {
	
	private EntityManagerFactory emf =Persistence.createEntityManagerFactory("webapp");
	
	private EntityManager em = emf.createEntityManager();
	CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	
	public AnforderungDao() {
	
	}
	
	
	

	
	
/*	public List<model.Anforderung> getAnfListe() {
		Query q = em.createQuery("select a from Anforderung a");
		List<model.Anforderung> anforderugen = q.getResultList();
		return anforderugen;
	}*/
	
	public List<model.Anforderung> findAll() {
		Query abfrage = em.createQuery("select a from Anforderung a");
		List<model.Anforderung> anforderugen = abfrage.getResultList();
		return anforderugen;
	}
	
	public model.Anforderung hinzuAnf(model.Anforderung anf) {
		em.getTransaction().begin();
		em.persist(anf);
		em.getTransaction().commit();
		return anf;
	}
	
	public model.Akzeptanzkriterium hinzuAkzept(Long anfId, model.Akzeptanzkriterium akzeptanz){
		model.Anforderung anforderung = findAnf(anfId);
		
		em.getTransaction().begin();
		em.persist(akzeptanz);
		em.getTransaction().commit();
		return akzeptanz;
	}

	
	public model.Anforderung findAnf(Long id) {
		return em.find(model.Anforderung.class, id);
	}
	
	public model.Anforderung updateAnf(model.Anforderung anf) {
		model.Anforderung anfToUpdate = findAnf(anf.getId());
		
		em.getTransaction().begin();
		anfToUpdate.setAnfNr(anf.getAnfNr());
		anfToUpdate.setAnfBezeichnung(anf.getAnfBezeichnung());
		anfToUpdate.setAnfBeschreibung(anf.getAnfBeschreibung());
		anfToUpdate.setAnfRisiko(anf.getAnfRisiko());
		anfToUpdate.setAnfKriterien(anf.getAnfKriterien());
		em.getTransaction().commit();
		return anfToUpdate;
		}






	public model.Anforderung getAnfAtIndex(int pos) {
		CriteriaQuery<model.Anforderung> cq = criteriaBuilder.createQuery(model.Anforderung.class);
		Root<model.Anforderung> variableRoot = cq.from(model.Anforderung.class);
		return em.createQuery(cq).setMaxResults(1).setFirstResult(pos).getSingleResult();
	}
	
	
	

	
	
	
}