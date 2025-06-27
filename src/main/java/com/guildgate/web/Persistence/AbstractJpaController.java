package com.guildgate.web.Persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * @author Juan - Luis
 */
public abstract class AbstractJpaController {

    public AbstractJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public AbstractJpaController() {
        emf = Persistence.createEntityManagerFactory("raid");
    }

    public EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void closeEntityManagerFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
