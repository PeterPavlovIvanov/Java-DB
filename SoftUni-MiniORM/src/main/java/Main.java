import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        em.doCreate(User.class);
        User testUser1 = new User("Pepino", "12345", 22, new Date());
        User testUser2 = new User("Stamat", "12345", 21, new Date());
        User testUser3 = new User("asdfg", "gsgsd", 341, new Date());
        em.persist(testUser1);
        em.persist(testUser2);
        em.persist(testUser3);
        em.persist(testUser1);

        String query = "SELECT * FROM users;";
        ResultSet rs = Connector.getConnection().prepareStatement(query).executeQuery();

        while (rs.next()) {
            System.out.printf("| %-2d | %-12s | %-12s | %-3d | %-10s |%n", rs.getInt("id"),
                    rs.getString("username"), rs.getString("password"),
                    rs.getInt("age"), rs.getDate("registration_date"));
        }
    }
}

