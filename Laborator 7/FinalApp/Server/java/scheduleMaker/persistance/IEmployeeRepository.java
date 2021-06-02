package scheduleMaker.persistance;

import scheduleMaker.model.Employee;

public interface IEmployeeRepository extends Repository<Integer, Employee> {
    Employee findOneByUsername(String username);
}
