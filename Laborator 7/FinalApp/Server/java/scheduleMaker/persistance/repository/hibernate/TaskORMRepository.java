package scheduleMaker.persistance.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import scheduleMaker.model.Employee;
import scheduleMaker.model.Task;
import scheduleMaker.model.User;
import scheduleMaker.persistance.ITaskRepository;
import scheduleMaker.services.ScheduleMakerException;

import java.util.ArrayList;
import java.util.List;

public class TaskORMRepository implements ITaskRepository {

    private static SessionFactory sessionFactory;


    public TaskORMRepository(){
        initilize();

    }

    @Override
    public Task findOne(Integer s) {
        return null;
    }

    private static void initilize(){
        System.out.println("Initializare");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Override
    public Task save(Task entity) {

            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                System.err.println("Exception "+e);
                StandardServiceRegistryBuilder.destroy( registry );
            }
            try(Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    session.save(entity);
                    tx.commit();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                    if (tx != null)
                        tx.rollback();
                }
            }
            return null;
    }

    @Override
    public void delete(Integer integer) {
    }

    @Override
    public Task update(Task entity) {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.update(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Task> findAll() {
        return null;
    }

    @Override
    public Iterable<Task> findEmployeeTasks(String username){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            List<Task> tasks=new ArrayList<>();
            try {
                tx = session.beginTransaction();
                String query="from Task as t where t.employee=:username";
                System.out.println(query);
                tasks = session.createQuery(query, Task.class)
                        .setString("username",username)
                        .list();
                tx.commit();
                System.out.println(tasks);
                return tasks;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public void close() {
        if (sessionFactory != null) sessionFactory.close();
    }
}
