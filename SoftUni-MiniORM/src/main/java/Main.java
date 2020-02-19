import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Scanner scanner = new Scanner(System.in);

        String username = "root";
        String password = "1234";
        String db = "orm_db";

        Connector.createConnection(username, password, db);
        EntityManager<User> em = new EntityManager<>(Connector.getConnection());

        User testUser = new User("testy", "test", 22, new Date());
        em.persist(testUser);
        try {
            User found = em.findFirst(User.class, " age > 18 ");
            System.out.println(found);

        } catch (InstantiationException e) {
e.printStackTrace();
        }

    }
}

