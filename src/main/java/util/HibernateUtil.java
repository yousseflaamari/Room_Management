package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            // Use the persistence unit name defined in persistence.xml
            entityManagerFactory = Persistence.createEntityManagerFactory("roomReservation");
        } catch (Exception e) {
            System.err.println("EntityManagerFactory initialization failed.");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
