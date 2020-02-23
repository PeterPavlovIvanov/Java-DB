import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
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
        int menu = 0;
        while (menu != 14) {
            System.out.println("\nEnter a number between [2-13] for an exercise. Enter 1 to exit.\n");
            menu = Integer.parseInt(this.scanner.nextLine());
            if (menu == 1) {
                break;                                                  // - - Exit.
            } else if (menu == 2) {
                this.exercise2RemoveObjects();                          // - - 2. Remove Objects - - TODO
            } else if (menu == 3) {
                this.exercise3ContainsEmployee();                       // - - 3. Contains Employee \/
            } else if (menu == 4) {
                this.exercise4SelectEmployees50000();                   // - - 4. Employees with Salary Over 50 000 \/
            } else if (menu == 5) {
                this.exercise5EmployeesFromDepartments();               // - - 5. Employees from Department \/
            } else if (menu == 6) {
                this.exercise6AddingANewAddressAndUpdatingEmployee();   // - - 6. Adding a New Address and Updating Employee \/
            } else if (menu == 7) {
                this.exercise7AddressesWithEmployeeCount();             // - - 7. Addresses with Employee Count - - \/
            } else if (menu == 8) {
                this.exercise8GetEmployeeWithProject();                 // - - 8.Get Employee with Project \/
            } else if (menu == 9) {
                this.exercise9FindLatestTenProjects();                  // - - 9.Find Latest 10 Projects \/
            } else if (menu == 10) {
                this.exercise10IncreaseSalaries();                      // - - 10.Increase Salaries \/
            } else if (menu == 11) {
                this.exercise11RemoveTowns();                           // - - 11. Remove Towns \/
            } else if (menu == 12) {
                this.exercise12FindEmployeesByFirstName();              // - - 12. Find Employees by First Name \/
            } else if (menu == 13) {
                this.exercise13EmployeesMaximumSalaries();              // - - 13. Employees Maximum Salaries \/
            } else {
                System.out.println("Invalid number.");
            }
        }
    }

    private void exercise2RemoveObjects() {
        List<Town> towns1 = this.entityManager
                .createQuery("SELECT t FROM Town AS t WHERE length(t.name) > 5", Town.class)
                .getResultList();

        List<Town> towns2 = this.entityManager
                .createQuery("SELECT t FROM Town AS t WHERE length(t.name) <= 5",Town.class)
                .getResultList();

        this.entityManager.getTransaction().begin();
        towns1.forEach(t1 -> this.entityManager.detach(t1));
        towns2.forEach(t2 -> t2.setName(t2.getName().toLowerCase()));
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void exercise3ContainsEmployee() {
        System.out.println("Enter employee's first and last name:");
        String full_name = this.scanner.nextLine();
        try {
            Employee employee = this.entityManager
                    .createQuery("SELECT e FROM Employee AS e " +
                            "WHERE concat(e.firstName, ' ',e.lastName) = :name", Employee.class)
                    .setParameter("name", full_name).getSingleResult();

            System.out.println("yes");
        } catch (NoResultException e) {
            System.out.println("No");
        }
    }

    private void exercise4SelectEmployees50000() {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee AS e WHERE e.salary > 50000", Employee.class)
                .getResultList();

        employees.forEach(e -> System.out.println(e.getFirstName()));
    }

    private void exercise5EmployeesFromDepartments() {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.department.name = 'Research and Development'" +
                        "ORDER BY e.salary ASC, e.id ASC", Employee.class)
                .getResultList();
        employees.forEach(e -> System.out.printf("%s %s %s %.2f %n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }

    private void exercise6AddingANewAddressAndUpdatingEmployee() {
        System.out.println("Enter employee's last name:");
        String lastName = this.scanner.nextLine();
        Employee employee;
        try {
            employee = this.entityManager
                    .createQuery("SELECT e FROM Employee AS e " +
                            "WHERE e.lastName = :name", Employee.class)
                    .setParameter("name", lastName).getSingleResult();
        } catch (NoResultException exception) {
            System.out.println("Invalid last name.");
            return;
        }
        Address address = new Address();
        address.setText("Vitoshka 15");
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager.detach(employee);
        employee.setAddress(address);
        this.entityManager.merge(employee);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void exercise7AddressesWithEmployeeCount() {
        List<Address> addresses = this.entityManager
                .createQuery("select a from Address as a " +
                        "group by a.id order by a.employees.size desc", Address.class)
                .setMaxResults(10).getResultList();

        addresses.forEach(a -> System.out.printf("%s, %s - %d employee(s)%n", a.getText(), a.getTown().getName(), a.getEmployees().size()));
    }

    private void exercise8GetEmployeeWithProject() {
        System.out.println("Enter employee's id:");
        int input = Integer.parseInt(this.scanner.nextLine());
        Employee employee;

        try {
            employee = this.entityManager.createQuery("SELECT e FROM Employee AS e WHERE e.id = :id", Employee.class).setParameter("id", input).getSingleResult();
            System.out.println(employee.getFirstName() + " " + employee.getLastName() + " - " + employee.getJobTitle());

            Set<String> projects = new TreeSet<>();
            employee.getProjects().forEach(p -> projects.add(p.getName()));
            projects.forEach(p -> System.out.printf("   %s%n", p));
        } catch (NoResultException exception) {
            System.out.println("Invalid id.");
        }
    }

    private void exercise9FindLatestTenProjects() {
        List<Project> projects = this.entityManager
                .createQuery("SELECT p FROM Project AS p ORDER BY p.startDate DESC", Project.class).setMaxResults(10).getResultList();
        projects.sort((a, b) -> a.getName().compareTo(b.getName()));
        projects.forEach(p -> System.out.printf("Project name: %s\n" +
                " \tProject Description: %s\n" +
                " \tProject Start Date:%s\n" +
                " \tProject End Date: %s\n", p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate()));
    }

    private void exercise10IncreaseSalaries() {
        List<Employee> employees = this.entityManager.createQuery("SELECT e FROM Employee AS e WHERE e.department.name = 'Engineering' OR " +
                "e.department.name = 'Tool Design' OR e.department.name = 'Marketing' " +
                "OR e.department.name = 'Information Services'", Employee.class).getResultList();

        this.entityManager.getTransaction().begin();
        employees.forEach(employee -> this.entityManager.detach(employee));
        employees.forEach(e -> e.setSalary(e.getSalary().multiply(new BigDecimal("1.12"))));
        employees.forEach(employee -> this.entityManager.merge(employee));
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        employees.forEach(e -> System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary()));
    }

    private void exercise11RemoveTowns() {
        System.out.println("Enter a town name:");
        String tName = this.scanner.nextLine();

        Town town = this.entityManager
                .createQuery("SELECT t FROM Town AS t " +
                        "WHERE t.name = :name", Town.class).setParameter("name", tName)
                .getSingleResult();

        List<Address> addresses = this.entityManager
                .createQuery("SELECT a FROM Address AS a " +
                        "WHERE a.town.id = :tId", Address.class)
                .setParameter("tId", town.getId()).getResultList();

        List<Employee> employees = this.entityManager
                .createQuery("SELECT employee FROM Employee AS employee " +
                        "JOIN Address AS adress ON adress.id = employee.address.id " +
                        "JOIN Town AS town ON town.id = adress.town.id " +
                        "WHERE town.name = :name", Employee.class).setParameter("name", tName).getResultList();


        this.entityManager.getTransaction().begin();

        this.entityManager.remove(town);
        addresses.forEach(a -> this.entityManager.remove(a));

        employees.forEach(e -> this.entityManager.detach(e));
        employees.forEach(e -> e.setAddress(null));
        employees.forEach(e -> this.entityManager.merge(e));

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        if (addresses.size() > 1) {
            System.out.printf("%d addresses in %s deleted%n", addresses.size(), tName);
        } else if (addresses.size() == 1) {
            System.out.printf("1 address in %s deleted%n", tName);
        } else {
            System.out.println("No such a town found.");
        }
    }

    private void exercise12FindEmployeesByFirstName() {
        System.out.println("Enter a pattern for a first name of an Employee (Case Sensitive):");
        String pattern = this.scanner.nextLine() + "%";
        List<Employee> employees = this.entityManager.createQuery("SELECT e FROM Employee AS e " +
                "WHERE e.firstName LIKE :pattern", Employee.class).setParameter("pattern", pattern).getResultList();

        employees.forEach(e -> System.out.printf("%s %s- %s - (%.2f)  %n", e.getFirstName(), e.getLastName(),
                e.getJobTitle(), e.getSalary()));
    }

    private void exercise13EmployeesMaximumSalaries() {

        List<BigDecimal> salaries = this.entityManager.createQuery(
                "SELECT MAX(e.salary) " +
                        "FROM Employee AS e " +
                        "JOIN Department AS d on e.department.id = d.id " +
                        "GROUP BY d.name " +
                        "HAVING MAX(e.salary) not between 30000 and 70000 "
                , BigDecimal.class).getResultList();

        List<String> departments = this.entityManager.createQuery(
                "SELECT d.name " +
                        "FROM Department AS d " +
                        "JOIN Employee AS e on e.department.id = d.id " +
                        "GROUP BY d.name " +
                        "HAVING MAX(e.salary) not between 30000 and 70000 "
                , String.class).getResultList();

        for (int i = 0; i < salaries.size(); i++) {
            System.out.println(departments.get(i) + " " + salaries.get(i));
        }
    }
}
