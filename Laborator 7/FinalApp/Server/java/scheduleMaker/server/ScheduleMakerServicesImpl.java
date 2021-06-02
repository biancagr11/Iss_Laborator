package scheduleMaker.server;

import scheduleMaker.model.Employee;
import scheduleMaker.model.Job;
import scheduleMaker.model.Task;
import scheduleMaker.model.User;
import scheduleMaker.persistance.IEmployeeRepository;
import scheduleMaker.persistance.ITaskRepository;
import scheduleMaker.persistance.IUserRepository;
import scheduleMaker.persistance.repository.database.EmployeeDatabaseRepository;
import scheduleMaker.services.IScheduleMakerObserver;
import scheduleMaker.services.IScheduleMakerServices;
import scheduleMaker.services.ScheduleMakerException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ScheduleMakerServicesImpl implements IScheduleMakerServices {

    private IEmployeeRepository employeeRepo;
    private IUserRepository userRepository;
    private ITaskRepository taskRepository;
    private List<IScheduleMakerObserver> adminClients=new ArrayList<>();
    private Map<String,IScheduleMakerObserver> regularClients=new ConcurrentHashMap<>();


    public ScheduleMakerServicesImpl(IEmployeeRepository employeeRepo, IUserRepository userRepository, ITaskRepository taskRepository){
        this.employeeRepo =employeeRepo;
        this.userRepository=userRepository;
        this.taskRepository=taskRepository;
    }

    @Override
    public List<Employee> findAllEmployes(){
        List<Employee> list=new ArrayList<>();
        employeeRepo.findAll().forEach(list::add);
        return list;

    }
    @Override
    public List<Task> findAllTasks(String employee){
        List<Task> list=new ArrayList<>();
        taskRepository.findEmployeeTasks(employee).forEach(list::add);
        return list;

    }
    @Override
    public Employee deleteEmploye(Integer id){
        //TODO
        employeeRepo.delete(id);
        return null;
    }
    @Override
    public Employee addEmployee(String firstName, String lastName, String job) throws RemoteException {
        Employee employee=new Employee(0,firstName,lastName, Job.valueOf(job),"null");
        Employee response= employeeRepo.save(employee);
        for(IScheduleMakerObserver client:adminClients)
            client.employeeAdded(employee);
        return response;
    }
    @Override
    public List<Employee> allDoctors(){
        return findAllEmployes().stream().filter(x->x.getJob()==Job.Medic).collect(Collectors.toList());
    }
    @Override
    public List<Employee> allNurses(){
        return findAllEmployes().stream().filter(x->x.getJob()==Job.Asistent).collect(Collectors.toList());
    }
    @Override
    public List<Employee> allOrderlies(){
        return findAllEmployes().stream().filter(x->x.getJob()==Job.Infirmier).collect(Collectors.toList());
    }
//    @Override
//    public void avoidEmployee(Employee employee){
//        involvedEmployees.remove(employee);
//        avoidedEmployees.add(employee);
//    }
//    @Override
//    public boolean involveEmployee(){
//        if(avoidedEmployees.size()==0){
//            return false;
//        }
//        involvedEmployees.add(avoidedEmployees.get(avoidedEmployees.size()-1));
//        avoidedEmployees.remove(avoidedEmployees.size()-1);
//        return true;
//    }
//    @Override
//    public List<Employee> allInvolvedEmployees(){
//        return involvedEmployees;
//    }

    @Override
    public String login(String username, String password, IScheduleMakerObserver client) throws ScheduleMakerException{
        User userR = userRepository.findByUsernameAndPassword(username, password);
        if (userR == null) {
            throw new ScheduleMakerException("No user with this username");
        }
        if(userR.getPermission().equals("admin")) {
            adminClients.add(client);
        }else{
            regularClients.put(username,client);
        }
        return userR.getPermission();
    }

    @Override
    public void logoutAdmin(IScheduleMakerObserver client) throws ScheduleMakerException {
        adminClients.remove(this);
    }
    @Override
    public void logoutRegular(String username) throws ScheduleMakerException {
        regularClients.remove(username);
    }

    @Override
    public void addTask(Task task) throws RemoteException{
        taskRepository.save(task);
        if(regularClients.containsKey(task.getEmployee())){
            regularClients.get(task.getEmployee()).taskAdded(task);
        }
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeRepo.findOneByUsername(username);
    }

    @Override
    public void updateTask(Task task, String newStatus) {
        Task t=new Task(task.getId(), task.getMessage(), newStatus, task.getEmployee());
        taskRepository.update(t);
    }
}
