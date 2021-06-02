package scheduleMaker.persistance;

import scheduleMaker.model.Task;

public interface ITaskRepository extends Repository<Integer, Task>{
    public Iterable<Task> findEmployeeTasks(String username);
}
