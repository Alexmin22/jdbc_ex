package org.example.utils.hibernate;

import org.example.entity.Company;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Company.class);
        return configuration;
    }
}
