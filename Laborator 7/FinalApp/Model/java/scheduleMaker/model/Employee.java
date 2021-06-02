package scheduleMaker.model;

import java.io.Serializable;

public class Employee extends Entity<Integer> implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private Job job;
    private String username;

    public Employee(){}
    public Employee(Integer id, String firstName, String lastName, Job job, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.job = job;
        this.username=username;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        String string=id+" ";
        if(job==Job.Asistent)
            string="As. ";
        else {
            if (job == Job.Medic)
                string = "Dr. ";
        }
        string=string+lastName+" "+firstName;
        return string;

    }
}
