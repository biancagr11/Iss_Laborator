package scheduleMaker.persistance.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import scheduleMaker.model.User;
import scheduleMaker.persistance.IUserRepository;
import scheduleMaker.services.ScheduleMakerException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserORMRepository implements IUserRepository {

    private static SessionFactory sessionFactory;


    public UserORMRepository(){
        initilize();

    }

    @Override
    public User findOne(String s) {
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
    public User save(User entity) {

    return null;
    }

    @Override
    public void delete(String integer) {
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws ScheduleMakerException {
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
            List<User> users=new ArrayList<>();
            try {
                tx = session.beginTransaction();
                String query="from User as u where u.username=:username and u.password=:password";
                users = session.createQuery(query, User.class)
                        .setString("username",username)
                        .setString("password",password)
                        .list();
                tx.commit();
                System.out.println("user: "+username+" "+password+" "+users.get(0));
                return users.get(0);
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
