import javax.persistence.EntityManager;
import java.util.*;

public class Engine implements Runnable {

    private final EntityManager entityManager;

    private final Scanner scanner;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {

    }
}
