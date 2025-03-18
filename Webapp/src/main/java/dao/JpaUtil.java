package dao;
import jakarta.persistence.*;

public class JpaUtil {
	
	
	
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("webapp");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
    
   
    
    
}