package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.util.Properties;

public class Util {


    public Session getSession() {
        Session session = null;
        if (session == null) {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            String URL = "jdbc:mysql://localhost:3306/users";
            settings.put(Environment.URL, URL);
            String USERNAME = "root";
            settings.put(Environment.USER, USERNAME);
            String PASSWORD = "Qz45909";
            settings.put(Environment.PASS, PASSWORD);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            session = sessionFactory.openSession();

        }
        System.out.println("Session Ok!");
        return session;
    }
}