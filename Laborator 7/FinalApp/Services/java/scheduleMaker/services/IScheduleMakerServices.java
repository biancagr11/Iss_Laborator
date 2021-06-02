package scheduleMaker.services;

import scheduleMaker.model.Employee;
import scheduleMaker.model.Task;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface IScheduleMakerServices {

     List<Employee> findAllEmployes();
     Employee deleteEmploye(Integer id);
     Employee addEmployee(String firstName, String lastName, String job) throws RemoteException;
     List<Employee> allDoctors();
     List<Employee> allNurses();
     List<Employee> allOrderlies();
     String login(String username, String password, IScheduleMakerObserver client) throws ScheduleMakerException;
     void logoutAdmin(IScheduleMakerObserver client) throws ScheduleMakerException;
     void logoutRegular(String client) throws ScheduleMakerException;
     void addTask(Task task) throws RemoteException;
     List<Task> findAllTasks(String employee);
     Employee getEmployeeByUsername(String username);
     void updateTask(Task task,String newStatus);
}
