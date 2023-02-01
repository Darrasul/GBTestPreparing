package org.buzas.lesson5.entities;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

public class FactoryCreator {

    public static EntityManagerFactory createFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}
