package scheduleMaker.services;

import scheduleMaker.model.Employee;
import scheduleMaker.model.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IScheduleMakerObserver extends Remote {

    void employeeAdded(Employee employee) throws RemoteException;
    void taskAdded(Task task) throws RemoteException;

}
