package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

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
        cq.select(criteriaBuilder.count(cq.from(model.Testfall.class)));
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
			System.out.println("speichern eine Anforderung");
			
			if(test.getTestId() == null || em.find(model.Testfall.class, test.getTestId()) == null) {
				em.persist(test);
			} else {
				em.merge(test);
			}
			
			
			for(model.Testschritte schritt : test.getTestschritte()) {
				schritt.setTest(test);
				model.Testschritte bestehenderSchritt; 

				addSchritte(schritt, test);
				
				if(schritt.getId() == null ||  em.find(model.Testschritte.class, schritt.getId()) == null) {
					addSchritte(schritt, test);
				} else {
					bestehenderSchritt = em.find(model.Testschritte.class, schritt.getId());
					updateSchritt(bestehenderSchritt, schritt);
				}
			}
			
			for(model.Voraussetzung voraussetzung : test.getVoraussetzungen()) {
				voraussetzung.setTest(test);
				model.Voraussetzung bestehendeVoraussetzung; 
				System.out.println("voraussetzung:  " + voraussetzung);
				addVoraussetzung(voraussetzung, test);
				
				if(voraussetzung.getId() == null ||  em.find(model.Voraussetzung.class, voraussetzung.getId()) == null) {
					addVoraussetzung(voraussetzung, test);
				} else {
					bestehendeVoraussetzung = em.find(model.Voraussetzung.class, voraussetzung.getId());
					updateVoraussetzung(bestehendeVoraussetzung, voraussetzung);
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
	
	public model.Testfall findTest(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Testfall test = em.find(model.Testfall.class, id);
		
	
		if (test != null && test.getTestschritte() != null) {
            // Initialisiere die Testschritte
            Hibernate.initialize(test.getTestschritte());
        } 
		if (test != null && test.getVoraussetzungen() != null) {
            // Initialisiere die Voraussetzungen
            Hibernate.initialize(test.getVoraussetzungen());
        }
		
		em.close();
		return test;
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
			testToUpdate.setAnmerkung(test.getAnmerkung());
			testToUpdate.setTestschritte(updateSchritte(test));
			testToUpdate.setVoraussetzungen(updateVoraussetzungen(test));

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
	
	

	private List<model.Testschritte> updateSchritte(model.Testfall test) {
		EntityManager em = JpaUtil.getEntityManager();
		List<model.Testschritte> schritte = new ArrayList<>();
		
		for(model.Testschritte schritt : test.getTestschritte()) {
			
			if(schritt.getId() == null) {
				
				addSchritte(schritt, test);
				schritte.add(schritt);
			} else {
				
				model.Testschritte bestehendesKriterium = em.find(model.Testschritte.class, schritt.getId());
				
				if(bestehendesKriterium != null) {
					updateSchritt(bestehendesKriterium, schritt);
					schritte.add(bestehendesKriterium);
				} else {
					System.out.println("Kriterium nicht gefunden");
				}
			}
			
		}
		return schritte;
		
	}

	
	public void addSchritte(model.Testschritte schritte, model.Testfall test) {
		EntityManager em = JpaUtil.getEntityManager();
		System.out.println("Schritte zu Test: " + test.getTestId());
		schritte.setTest(test);
		
		try {
			em.getTransaction().begin();
			em.persist(schritte);
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
	
	public void intialisiereTestschritte(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		model.Testfall test= em.find(model.Testfall.class, id);
		System.out.println("initialiserung der testchritte");
		
		
		if (test != null && test.getTestschritte() != null) {
            // Initialisiere die Testschritte
            Hibernate.initialize(test.getTestschritte());
            System.out.println("Die Testschritte wurden erfolgreich initialisiert.");
        } else {
            System.out.println("Der Testfall oder die Testschritte sind null.");
        }
           
        
	}
	
	public void updateSchritt(model.Testschritte altSchritte, model.Testschritte neuSchritte) {
		altSchritte.setSchrittBeschr(neuSchritte.getSchrittBeschr());
		
	}
	
	
	public void deleteSchritt(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			model.Testschritte kriterium = em.find(model.Testschritte.class, id);
			System.out.println("kriterium (Methode): "+  kriterium.getId() );
			if(kriterium != null ) {
				em.remove(kriterium);
			}
			
			
			model.Testschritte proof = em.find(model.Testschritte.class, id);
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
	
	
	
	
	
	private List<model.Voraussetzung> updateVoraussetzungen(model.Testfall test) {
		EntityManager em = JpaUtil.getEntityManager();
		List<model.Voraussetzung> voraussetzungen = new ArrayList<>();
		
		for(model.Voraussetzung voraussetzung : test.getVoraussetzungen()) {
			
			if(voraussetzung.getId() == null) {
				
				addVoraussetzung(voraussetzung, test);
				voraussetzungen.add(voraussetzung);
			} else {
				
				model.Voraussetzung bestehendesKriterium = em.find(model.Voraussetzung.class, voraussetzung.getId());
				
				if(bestehendesKriterium != null) {
					updateVoraussetzung(bestehendesKriterium, voraussetzung);
					voraussetzungen.add(bestehendesKriterium);
				} else {
					System.out.println("Kriterium nicht gefunden");
				}
			}
			
		}
		return voraussetzungen;
		
	}
	
	
	
	
	
	public void addVoraussetzung(model.Voraussetzung voraussetzung, model.Testfall test) {
		EntityManager em = JpaUtil.getEntityManager();
		System.out.println("Schritte zu Test: " + test.getTestId());
		voraussetzung.setTest(test);
		
		try {
			em.getTransaction().begin();
			em.persist(voraussetzung);
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
	public void updateVoraussetzung(model.Voraussetzung altVoraussetzung, model.Voraussetzung neuVoraussetzung) {
		altVoraussetzung.setVoraussetzungBeschr(neuVoraussetzung.getVoraussetzungBeschr());
		
	}
	
	
	
	public void deleteVoraussetzung(Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			model.Voraussetzung voraussetzung = em.find(model.Voraussetzung.class, id);
			System.out.println("voraussetzung (Methode): "+  voraussetzung.getId() );
			if(voraussetzung != null ) {
				em.remove(voraussetzung);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}