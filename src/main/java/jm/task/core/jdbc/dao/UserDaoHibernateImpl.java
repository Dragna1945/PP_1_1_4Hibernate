package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = util.getSession()) {
            Transaction transaction = session.beginTransaction();

            String hqlCreateTable = "CREATE TABLE IF NOT EXISTS USER" +
                    "(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "NAME VARCHAR(45) NOT NULL," +
                    "LASTNAME VARCHAR(45) NOT NULL," +
                    "AGE TINYINT(255) NOT NULL)";
            session.createSQLQuery(hqlCreateTable).addEntity(User.class).executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = util.getSession()) {
            Transaction transaction = session.beginTransaction();

            String hqlDropTable = "DROP table if exists user";
            session.createSQLQuery(hqlDropTable).executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = util.getSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Error in sql query");
            }
            System.out.println("Error initializing: transaction == null");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = util.getSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Error in sql query");
            }
            System.out.println("Error initializing: transaction == null");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = util.getSession()) {
            String hqlFU = "FROM User";
            TypedQuery<User> query = session.createQuery(hqlFU);
            List<User> userList = query.getResultList();
            return userList;
        }
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = util.getSession()) {
            transaction = session.beginTransaction();

            String hqlDelete = "delete FROM User";
            session.createQuery(hqlDelete).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Error in sql query");
            }
            System.out.println("Error initializing: transaction == null");
        }
    }
}