package scheduleMaker.model;

import java.io.Serializable;

public class User extends Entity<String> implements Serializable {
    private String username;
    private String password;
    private String permission;
    private Integer employee;

    public User(){}

    public User(String username, String password, String permission, Integer employee) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.employee = employee;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPermission() {
        return permission;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission='" + permission + '\'' +
                ", employee=" + employee +
                '}';
    }
}

