package scheduleMaker.persistance.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import scheduleMaker.model.Employee;
import scheduleMaker.model.Job;
import scheduleMaker.model.User;
import scheduleMaker.persistance.IEmployeeRepository;
import scheduleMaker.persistance.repository.database.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeORMRepository implements IEmployeeRepository {

    private static SessionFactory sessionFactory;

    public EmployeeORMRepository() {
        initilize();
    }
    private static void initilize(){
        System.out.println("Initializare");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    @Override
    public Employee findOne(Integer id){
//        try (
//                Connection connection = dbUtils.getConnection();
//                PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id="+id);
//                ResultSet resultSet = statement.executeQuery();
//        ) {
//
//            resultSet.next();
//            String firstName = resultSet.getString("firstName");
//            String lastName = resultSet.getString("lastName");
//            String job=resultSet.getString("job");
//            return new Employee(id, firstName, lastName, Job.valueOf(job));
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
        return null;

    }

    @Override
    public Iterable<Employee> findAll(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<Employee> employees=new ArrayList<>();
            try {
                tx = session.beginTransaction();
                String query="from Employee";
                employees = session.createQuery(query, Employee.class)
                        .list();
                tx.commit();
                return employees;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Employee save(Employee entity){
//        try (Connection connection = dbUtils.getConnection();
//             PreparedStatement statement = connection.prepareStatement("INSERT INTO employees(firstName,lastName,job) VALUES(?,?,?)");
//        ) {
//            statement.setString(1, entity.getFirstName());
//            statement.setString(2, entity.getLastName());
//            statement.setString(3, entity.getJob().name());
//            statement.execute();
//        }catch(SQLException ex) {
//            ex.printStackTrace();
//            return null;
//        }
        return entity;
    }

    @Override
    public void delete(Integer id){
//        try(
//                Connection connection = dbUtils.getConnection();
//                PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
//        ){
//            statement.setInt(1, Integer.parseInt(id.toString()));
//            statement.executeUpdate();
//        }catch(SQLException ex) {
//            ex.printStackTrace();
//        }
    }

    @Override
    public Employee update(Employee entity){
        return null;
    }

    @Override
    public Employee findOneByUsername(String username) {
        return null;
    }
}
