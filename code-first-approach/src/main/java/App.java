import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    private static final String GRINGOTTS_PU = "gringotts_db";
    private static final String SALES_PU = "sales_db";
    private static final String UNIVERSITY_PU = "university_db";
    private static final String HOSPITAL_PU = "hospital_db";
    private static final String PAYMENT_SYSTEM_PU = "paymentSystem_db";
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PAYMENT_SYSTEM_PU);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Engine engine = new Engine(entityManager);
        engine.run();
    }
}
