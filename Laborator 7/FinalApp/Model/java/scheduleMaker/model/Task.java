package scheduleMaker.model;

import java.io.Serializable;

public class Task extends Entity<Integer> implements Serializable {

    private Integer id;
    private String message;
    private String status;
    private String employee;

    public Task(){}

    public Task(Integer id, String message, String status, String employee) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.employee = employee;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getEmployee() {
        return employee;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmployee(String username) {
        this.employee = username;
    }

    @Override
    public String toString() {
        return message+"---->"+status;
    }
}
