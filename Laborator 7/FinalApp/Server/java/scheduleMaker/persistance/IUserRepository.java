package scheduleMaker.persistance;

import scheduleMaker.model.Employee;
import scheduleMaker.model.User;
import scheduleMaker.services.ScheduleMakerException;

public interface IUserRepository extends Repository<String, User>{
    public User findByUsernameAndPassword(String username, String password) throws ScheduleMakerException;

}
