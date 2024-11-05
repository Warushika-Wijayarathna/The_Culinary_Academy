package com.zenveus.the_culinary_academy.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {

        // Set Hibernate properties programmatically
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        hibernateProperties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/culinary_academy");
        hibernateProperties.setProperty("hibernate.connection.username", "root");
        hibernateProperties.setProperty("hibernate.connection.password", "Ijse@1234");

        // Additional Hibernate settings
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");

        // Configure Hibernate with properties and add annotated classes
        Configuration configuration = new Configuration()
                .addProperties(hibernateProperties)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Program.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Student.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.StudentProgram.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.User.class)
                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Payment.class);

        // Build the session factory
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}

//package com.zenveus.the_culinary_academy.config;
//
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class FactoryConfiguration {
//    private static FactoryConfiguration factoryConfiguration;
//    private SessionFactory sessionFactory;
//
//    private FactoryConfiguration() {
//        Configuration configuration = new Configuration().configure()
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Program.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Student.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.StudentProgram.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.User.class)
//                .addAnnotatedClass(com.zenveus.the_culinary_academy.entity.Payment.class);
//        sessionFactory = configuration.configure().buildSessionFactory();
//    }
//
//    public static FactoryConfiguration getInstance() {
//        return (factoryConfiguration == null) ? factoryConfiguration =
//                new FactoryConfiguration() : factoryConfiguration;
//    }
//
//    public Session getSession() {
//        return sessionFactory.openSession();
//    }
//}