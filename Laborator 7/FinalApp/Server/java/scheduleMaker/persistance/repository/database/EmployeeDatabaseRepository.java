package scheduleMaker.persistance.repository.database;

import scheduleMaker.model.Employee;
import scheduleMaker.model.Job;
import scheduleMaker.persistance.IEmployeeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeDatabaseRepository implements IEmployeeRepository {
    private JdbcUtils dbUtils;

    public EmployeeDatabaseRepository(Properties props) {
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public Employee findOne(Integer id){
        try (
                Connection connection = dbUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id="+id);
                ResultSet resultSet = statement.executeQuery();
        ) {

            resultSet.next();
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String job=resultSet.getString("job");
            String username=resultSet.getString("username");
            return new Employee(id, firstName, lastName, Job.valueOf(job),username);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<Employee> findAll(){
        List<Employee> employees=new ArrayList<>();
        try (
                Connection connection = dbUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees");
                ResultSet resultSet = statement.executeQuery();
        ) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String job=resultSet.getString("job");
                String username=resultSet.getString("username");
                employees.add(new Employee(id, firstName, lastName, Job.valueOf(job),username));

            }
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(employees);
        return employees;
    }

    @Override
    public Employee save(Employee entity){
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO employees(firstName,lastName,job) VALUES(?,?,?)");
        ) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getJob().name());
            statement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return entity;
    }

    @Override
    public void delete(Integer id){
        try(
                Connection connection = dbUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
        ){
            statement.setInt(1, Integer.parseInt(id.toString()));
            statement.executeUpdate();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Employee update(Employee entity){
        return null;
    }

    @Override
    public Employee findOneByUsername(String username) {
        try (
                Connection connection = dbUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE username=?");
        ) {
            statement.setString(1,username);
            try(ResultSet resultSet = statement.executeQuery();
            ) {
                resultSet.next();
                Integer id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String job = resultSet.getString("job");
                return new Employee(id, firstName, lastName, Job.valueOf(job), username);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
