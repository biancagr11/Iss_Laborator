package scheduleMaker.client.gui;

import scheduleMaker.model.Employee;
import scheduleMaker.model.Job;
import scheduleMaker.model.Task;
import scheduleMaker.model.User;
import scheduleMaker.services.IScheduleMakerObserver;
import scheduleMaker.services.IScheduleMakerServices;
import scheduleMaker.services.ScheduleMakerException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientController extends UnicastRemoteObject implements IScheduleMakerObserver, Serializable {

    private IScheduleMakerServices server;
    private ControllerEmployeeScene employeeScene;
    private ControllerRegularWindow controllerRegularWindow;

    private List<Employee> involvedEmployees=new ArrayList<>();
    private List<Employee> avoidedEmployees=new ArrayList<>();


    public ClientController(IScheduleMakerServices server) throws RemoteException{
        this.server=server;
    }

    public String login(String username, String password) throws ScheduleMakerException {
        return server.login(username, password, this);
    }
    public void logout() throws ScheduleMakerException {
        server.logoutAdmin(this);
    }
    public void logoutRegular(String username) throws ScheduleMakerException {
        server.logoutRegular(username);
    }
    public List<Employee> findAllEmployees(){
        return server.findAllEmployes();
    }
    public Employee addEmployee(String firstName, String lastName, String job){
        try{
            return server.addEmployee(firstName,lastName,job);
        }catch (RemoteException ex){
            ex.printStackTrace();
        }
        return null;
    }
    public Employee deleteEmployee(Integer id){
        return server.deleteEmploye(id);
    }

    public void setEmployeeScene(ControllerEmployeeScene employeeScene) {
        this.employeeScene = employeeScene;
    }

    @Override
    public void employeeAdded(Employee employee) throws RemoteException {
        employeeScene.employeeAdded(employee);
    }

    public List<Employee> allDoctors(){
        avoidedEmployees.clear();
        involvedEmployees=server.allDoctors();
        return involvedEmployees;
    }
    public List<Employee> allNurses(){
        avoidedEmployees.clear();
        involvedEmployees=server.allNurses();
        return involvedEmployees;
    }
    public List<Employee> allOrderlies(){
        avoidedEmployees.clear();
        involvedEmployees=server.allOrderlies();
        return involvedEmployees;
    }
    public void avoidEmployee(Employee employee){
        involvedEmployees.remove(employee);
        avoidedEmployees.add(employee);
    }
    public boolean involveEmployee(){
        if(avoidedEmployees.size()==0){
            return false;
        }
        involvedEmployees.add(avoidedEmployees.get(avoidedEmployees.size()-1));
        avoidedEmployees.remove(avoidedEmployees.size()-1);
        return true;
    }
    public List<Employee> allInvolvedEmployees(){
        return involvedEmployees;
    }

    public void addTask(String message, String status, String employee){
        try{
            server.addTask(new Task(0, message, status, employee));
        }catch (RemoteException ex){
            MessageBox.showErrorMessage(ex.getMessage());
        }
    }
    public List<Task> findAllTasks(String employee){
        return server.findAllTasks(employee);
    }

    public Employee getEmployeeByUsername(String username){
        return server.getEmployeeByUsername(username);
    }

    public void updateTask(Task t, String status){
        server.updateTask(t,status);
    }

    @Override
    public void taskAdded(Task task) throws RemoteException {
        controllerRegularWindow.addTask(task);
    }

    public void setControllerRegularWindow(ControllerRegularWindow controllerRegularWindow) {
        this.controllerRegularWindow = controllerRegularWindow;
    }
}
