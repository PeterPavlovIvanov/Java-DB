import java.sql.*;
import java.util.*;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";

    private static Connection connection;
    private static String query;
    private static PreparedStatement statement;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root"); //TODO: Don't forget to change the props.
        properties.setProperty("password", "1234");
        connection = DriverManager.getConnection(CONNECTION_STRING + DATABASE_NAME, properties);

        System.out.println("Select an exercise by entering a number from 2 to 9, enter 10 to exit.");
        int menu = Integer.parseInt(scanner.nextLine());
        while (menu != 10) {
            if (menu == 2) {
                System.out.println(" - - 2. Get Villains’ Names - - ");
                exercise_Get_Villains_Names_And_Count_Of_Minions();
            } else if (menu == 3) {
                System.out.println(" - - 3. Get Minion Names - - ");
                exercise_Get_A_Given_Villains_Minions();
            } else if (menu == 4) {
                System.out.println(" - - 4. Add Minion - - ");
                exercise_Add_Minion_Exercise();
            } else if (menu == 5) {
                System.out.println(" - - 5. Change Town Names Casing - - ");
                exercise_Change_Towns_To_UPPER_CASE_By_Country();
            } else if (menu == 6) {
                System.out.println("I haven't done the 6th exercise yet :)");
            } else if (menu == 7) {
                System.out.println(" - - 7. Print All Minion Names - - ");
                exercise_Print_All_Minions_Names_In_A_Specific_Order();
            } else if (menu == 8) {
                System.out.println(" - - 8. Increase Minions Age - - ");
                exercise_Update_Minions_By_Given_IDs();
            } else if (menu == 9) {
                System.out.println(" - - 9. Increase Age Stored Procedure - - ");
                exercise_Update_Minion_Age_By_ID();
            } else {
                System.out.println("Incorrect number. Try again with [2-10]");
            }
            System.out.println("\nEnter a number [2-10]\n");
            menu = Integer.parseInt(scanner.nextLine());
        }

    }

    private static void exercise_Update_Minion_Age_By_ID() throws SQLException {
        int ID = Integer.parseInt(scanner.nextLine());
        query = "CALL usp_get_older(?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1,ID);
        statement.executeQuery();

        query = "SELECT name, age FROM minions WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,ID);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            System.out.printf("The updated Minion: [name - %s, age - %d]%n", rs.getString("name"),rs.getInt("age"));
        }
    }

    private static void exercise_Update_Minions_By_Given_IDs() throws SQLException {
        String[] IDs = scanner.nextLine().split("\\s+");
        query = "UPDATE minions SET age = age + 1, name = LCASE(name) WHERE id = ?";
        statement = connection.prepareStatement(query);
        for (int i = 0; i < IDs.length; i++) {
            int currentMinionID = Integer.parseInt(IDs[i]);
            statement.setInt(1, currentMinionID);
        }
        statement.executeUpdate();
        query = "SELECT name, age FROM minions";
        statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("name") + " " + rs.getInt("age"));
        }
    }

    private static void exercise_Print_All_Minions_Names_In_A_Specific_Order() throws SQLException {
        query = "SELECT name FROM minions";
        statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        Deque<String> names = new LinkedList<String>();
        while (rs.next()) {
            names.offer(rs.getString(1));
        }

        while (!names.isEmpty()) {
            System.out.println(names.pollFirst());
            System.out.println(names.pollLast());
        }
    }

    private static void exercise_Change_Towns_To_UPPER_CASE_By_Country() throws SQLException {
        String country = scanner.nextLine();
        query = "UPDATE towns SET name = UCASE(name)\n" +
                "where country = ?;\n";
        statement = connection.prepareStatement(query);
        statement.setString(1, country);
        statement.executeUpdate();

        query = "SELECT name FROM towns WHERE country = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, country);
        ResultSet rs = statement.executeQuery();

        int counter = 0;
        List<String> towns = new ArrayList<>();
        while (rs.next()) {
            towns.add(rs.getString(1));
            counter++;
        }
        if (counter > 0) {
            System.out.println("" + counter + " town names were affected. \n" + towns);
        } else {
            System.out.println("No town names were affected.");
        }
    }

    private static void exercise_Add_Minion_Exercise() throws SQLException {
        System.out.print("Enter minion parameters: ");
        String[] minionParameters = scanner.nextLine().split("\\s+");
        String minionName = minionParameters[0];
        int minionAge = Integer.parseInt(minionParameters[1]);
        String minionTown = minionParameters[2];

        System.out.print("Enter villain name: ");
        String villainName = scanner.nextLine();

        if (!check_If_Entity_Exists_By_Name(minionTown, "towns")) {
            insert_Entity_In_Town(minionTown);
        }

        if (!check_If_Entity_Exists_By_Name(villainName, "villains")) {
            insert_Entity_In_Villains(villainName);
        }

        int town_id = get_Id_By_Name(minionTown, "towns");

        if (!check_If_Entity_Exists_By_Name(minionName, "minions")) {
            insert_Entity_In_Minions(minionName, minionAge, town_id);
        }

        int minion_id = get_Id_By_Name(minionName, "minions");
        int villain_id = get_Id_By_Name(villainName, "villains");
        query = "INSERT INTO minions_villains(minion_id, villain_id) value(?, ?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1, minion_id);
        statement.setInt(2, villain_id);
        statement.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);
    }

    private static int get_Id_By_Name(String name, String tableName) throws SQLException {
        query = "SELECT id FROM " + tableName + " WHERE name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private static void insert_Entity_In_Minions(String minionName, int minionAge, int town_id) throws SQLException {
        query = "insert into minions(name, age, town_id) value(?, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, minionName);
        statement.setInt(2, minionAge);
        statement.setInt(3, town_id);
        statement.executeUpdate();
    }

    private static void insert_Entity_In_Villains(String villainName) throws SQLException {
        query = "insert into villains(name, evilness_factor) value(?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        statement.setString(2, "evil");
        statement.executeUpdate();
        System.out.printf("Villain %s was added to the database.%n", villainName);
    }

    private static void insert_Entity_In_Town(String minionTown) throws SQLException {
        query = "insert into towns(name, country) value(?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, minionTown);
        statement.setString(2, null);
        statement.executeUpdate();
        System.out.printf("Town %s was added to the database.%n", minionTown);
    }

    private static boolean check_If_Entity_Exists_By_Name(String entityName, String tableName) throws SQLException {
        query = "select * from " + tableName + " where name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, entityName);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    private static void exercise_Get_A_Given_Villains_Minions() throws SQLException {
        System.out.print("Enter villain id: ");
        int villain_id = Integer.parseInt(scanner.nextLine());

        if (check_If_Entity_Exists(villain_id, "villains")) {
            System.out.printf("Villain: %s%n", get_Entity_Name_By_Id(villain_id, "villains"));
            get_Minions_And_Age_By_Id(villain_id);
        } else {
            System.out.printf("No villain with ID %d exists in the database.%n", villain_id);
        }
    }

    private static void get_Minions_And_Age_By_Id(int villain_id) throws SQLException {
        query = "select m.name, m.age\n" +
                "from minions as m\n" +
                "join minions_villains as mv\n" +
                "on m.id = mv.minion_id\n" +
                "where mv.villain_id = ?;";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villain_id);
        ResultSet rs = statement.executeQuery();
        int minionsCounter = 1;
        while (rs.next()) {
            System.out.printf("%d. %s %d%n", minionsCounter, rs.getString("name"), rs.getInt("age"));
            minionsCounter++;
        }
    }

    private static String get_Entity_Name_By_Id(int entity_id, String tableName) throws SQLException {
        query = "select * from " + tableName + " where id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, entity_id);
        ResultSet rs = statement.executeQuery();
        return rs.next() ? rs.getString("name") : null;
    }

    private static boolean check_If_Entity_Exists(int id, String tableName) throws SQLException {
        query = "select * from " + tableName + " where id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    private static void exercise_Get_Villains_Names_And_Count_Of_Minions() throws SQLException {
        query = "select v.name, count(mv.minion_id) as 'count'\n" +
                "from villains as v\n" +
                "join minions_villains as mv on v.id = mv.villain_id\n" +
                "group by v.name having `count` > 15 order by `count` desc;";

        statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("count"));
        }
    }
}
