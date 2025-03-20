package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
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
	
	
	
	public void addMitarbeiter(model.Anforderung anforderung) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		/*model.Mitarbeiter ersteller = em.find(model.Mitarbeiter.class, erstellerID);
		
		anforderung.setErsteller(ersteller);
		ersteller.getErstellteAnf().add(anforderung);*/
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
		
		if (anf != null && anf.getAnfKriterien() != null) {
            // Initialisiere die anf
            Hibernate.initialize(anf.getAnfKriterien());
        } 
		
		em.close();
		return anf;
	}

	
	
	/*speichert Anforderung und deren Ersteller*/
	public void saveAnf(long mitarbeiterID, model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		
		try {
			//Mitarbeiter festlegen
			anf.setErsteller(em.find(model.Arbeiter.class, mitarbeiterID));
			
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
			prüfeAnf(anf, anf.getAnfId());
			
			anfToUpdate.setAnfNr(anf.getAnfNr());
			anfToUpdate.setAnfBezeichnung(anf.getAnfBezeichnung());
			anfToUpdate.setAnfBeschreibung(anf.getAnfBeschreibung());
			anfToUpdate.setAnfZiel(anf.getAnfZiel());
			anfToUpdate.setAnfRisiko(anf.getAnfRisiko());
			//überarbeiten -> id wird nicht geladen
			
			List<model.Akzeptanzkriterium> kriterien = updateKriterien(anf);
			
			
			anfToUpdate.setAnfKriterien(kriterien);
			em.merge(anfToUpdate);
			em.getTransaction().commit();
			
			
			
		} catch (Exception  e) {
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
			e.printStackTrace(); 
		} finally {
			em.close();
		}

		return anfToUpdate;
		
	
		}
	
	public void deleteAnf(model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		
		System.out.println("anforderung löschen: " + anf.getAnfId());
		
		try {
			em.getTransaction().begin();
			
			model.Anforderung löschendeAnf = em.find(model.Anforderung.class, anf.getAnfId());
			if(löschendeAnf != null) {
				em.remove(löschendeAnf);
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
	
	
	public void deleteRequirement(Integer requirementId) {
		EntityManager em = JpaUtil.getEntityManager();

	    try {
	        em.getTransaction().begin();

	        // Anforderung finden
	        model.Anforderung requirement = em.find(model.Anforderung.class, requirementId);
	        if (requirement == null) {
	            throw new IllegalArgumentException("Anforderung nicht gefunden.");
	        }

	        
	        for(model.Akzeptanzkriterium kriterium : requirement.getAnfKriterien()) {
	        	deleteKriterium(kriterium.getId());
	        }
	        // Alle verbundenen Kriterien werden automatisch gelöscht,
	        // wenn CascadeType.ALL und orphanRemoval=true gesetzt sind.
	        em.remove(requirement); // Anforderung löschen

	        em.getTransaction().commit();
	    } catch (IllegalArgumentException e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        System.out.println(e.getMessage());
	    } catch (PersistenceException e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        System.out.println("Fehler beim Löschen: " + e.getMessage());
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close(); // EntityManager schließen
	    }
	}
	
	//prüft die Existenz einer Anforderung
	public void prüfeAnf(model.Anforderung anf, Integer id) {
		if (anf == null) {
            throw new IllegalArgumentException("Anforderung nicht gefunden: " + anf.getAnfId());
        }
	}
	
	public void addKriterium(model.Akzeptanzkriterium kriterium, model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		kriterium.setAnforderung(anf);
		
		try {
			em.getTransaction().begin();
			em.persist(kriterium);
			em.getTransaction().commit();
		} catch(Exception e) {
			if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback(); // Rollback bei Fehlern
	        }
		     e.printStackTrace();
	    } finally {
	        em.close();
	    
	    }	
		
	}

	public void updateKriterium(model.Akzeptanzkriterium altKriterium, model.Akzeptanzkriterium neuKriterium) {
		altKriterium.setAkzeptanzBeschr(neuKriterium.getAkzeptanzBeschr());
		
	}
	
	
	
	private List<model.Akzeptanzkriterium> updateKriterien(model.Anforderung anf) {
		EntityManager em = JpaUtil.getEntityManager();
		List<model.Akzeptanzkriterium> kriterien = new ArrayList<>();
		
		for(model.Akzeptanzkriterium kriterium : anf.getAnfKriterien()) {
			
			if(kriterium.getId() == null) {
				System.out.println("Kriterium hat  kein Id");
				System.out.println(kriterium);
				addKriterium(kriterium, anf);
				kriterien.add(kriterium);
			} else {
				System.out.println("kriterium speichern: "+ kriterium.getId());
				model.Akzeptanzkriterium bestehendesKriterium = em.find(model.Akzeptanzkriterium.class, kriterium.getId());
				
				if(bestehendesKriterium != null) {
					System.out.println(kriterium);
					System.out.println("eine bearbeites Kriterium");
					updateKriterium(bestehendesKriterium, kriterium);
					kriterien.add(bestehendesKriterium);
				} else {
					System.out.println("Kriterium nicht gefunden");
				}
			}
			
			
			
		}
		return kriterien;
		
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
	
	public void deleteKriterium(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			model.Akzeptanzkriterium kriterium = em.find(model.Akzeptanzkriterium.class, id);
			System.out.println("kriterium (Methode): "+  kriterium.getId() );
			if(kriterium != null ) {
				em.remove(kriterium);
			}
			
			
			model.Akzeptanzkriterium proof = em.find(model.Akzeptanzkriterium.class, id);
			if(proof == null) {
				System.out.println("K gelöscht");

			} else {
				System.out.println("K gelöscht");
			}
	        em.getTransaction().commit();

		} catch(Exception e) {
			 if (em.getTransaction().isActive()) {
		            em.getTransaction().rollback();
		        }
		        e.printStackTrace();
	    } finally {
	        em.close(); // EntityManager schließen
	    }
		
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
	

	
	public Integer getNextId() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNativeQuery(
				"SELECT next_val FROM 'anforderung_seq'");
		return ((Integer) query.getSingleResult()).intValue();
	}
	
	public String maxAnfNr() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createQuery("SELECT MAX(a.anfNr) FROM Anforderung a");
		String maxNr =(String) query.getSingleResult();
		return maxNr;
	}
	
	
	
	public model.Anforderung getLetzteAnf() {
		EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Anforderung> query = em.createQuery(
            "SELECT a FROM Anforderung a ORDER BY a.anfId DESC", Anforderung.class);
        query.setMaxResults(1);
        List<model.Anforderung> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

	
}