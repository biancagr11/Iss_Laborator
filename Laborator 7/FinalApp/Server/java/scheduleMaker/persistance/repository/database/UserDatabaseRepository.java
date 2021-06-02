package scheduleMaker.persistance.repository.database;

import scheduleMaker.model.User;
import scheduleMaker.persistance.IUserRepository;
import scheduleMaker.services.ScheduleMakerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDatabaseRepository implements IUserRepository {
    private JdbcUtils dbUtils;

    public UserDatabaseRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public User save(User entity) {
       return null;
    }

    @Override
    public void delete(String integer) {
    }
    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User findOne(String integer) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws ScheduleMakerException {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from users where username=?")){
            preStmt.setString(1,username);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String passwordFound=result.getString("password");
                    if(!password.equals(passwordFound)) {
                        throw new ScheduleMakerException("Wrong password!");
                    }
                    User user = new User(username,password,"all",1);
                    return user;
                }
            }
        }catch (SQLException ex){
            throw new ScheduleMakerException(ex.getMessage());
        }
        return null;
    }
}
