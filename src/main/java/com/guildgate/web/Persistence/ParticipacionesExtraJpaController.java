/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guildgate.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Participaciones;
import com.guildgate.web.Modelo.ParticipacionesExtra;
import com.guildgate.web.Modelo.Ronda;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author hp
 */
public class ParticipacionesExtraJpaController implements Serializable {

    public ParticipacionesExtraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParticipacionesExtra participacionesExtra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Participaciones parti = participacionesExtra.getParti();
            if (parti != null) {
                parti = em.getReference(parti.getClass(), parti.getId());
                participacionesExtra.setParti(parti);
            }
            Ronda partiextraronda = participacionesExtra.getPartiextraronda();
            if (partiextraronda != null) {
                partiextraronda = em.getReference(partiextraronda.getClass(), partiextraronda.getId());
                participacionesExtra.setPartiextraronda(partiextraronda);
            }
            em.persist(participacionesExtra);
            if (parti != null) {
                parti.getListaPartiExtra().add(participacionesExtra);
                parti = em.merge(parti);
            }
            if (partiextraronda != null) {
                partiextraronda.getListaParticipacionesExtra().add(participacionesExtra);
                partiextraronda = em.merge(partiextraronda);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParticipacionesExtra participacionesExtra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParticipacionesExtra persistentParticipacionesExtra = em.find(ParticipacionesExtra.class, participacionesExtra.getId());
            Participaciones partiOld = persistentParticipacionesExtra.getParti();
            Participaciones partiNew = participacionesExtra.getParti();
            Ronda partiextrarondaOld = persistentParticipacionesExtra.getPartiextraronda();
            Ronda partiextrarondaNew = participacionesExtra.getPartiextraronda();
            if (partiNew != null) {
                partiNew = em.getReference(partiNew.getClass(), partiNew.getId());
                participacionesExtra.setParti(partiNew);
            }
            if (partiextrarondaNew != null) {
                partiextrarondaNew = em.getReference(partiextrarondaNew.getClass(), partiextrarondaNew.getId());
                participacionesExtra.setPartiextraronda(partiextrarondaNew);
            }
            participacionesExtra = em.merge(participacionesExtra);
            if (partiOld != null && !partiOld.equals(partiNew)) {
                partiOld.getListaPartiExtra().remove(participacionesExtra);
                partiOld = em.merge(partiOld);
            }
            if (partiNew != null && !partiNew.equals(partiOld)) {
                partiNew.getListaPartiExtra().add(participacionesExtra);
                partiNew = em.merge(partiNew);
            }
            if (partiextrarondaOld != null && !partiextrarondaOld.equals(partiextrarondaNew)) {
                partiextrarondaOld.getListaParticipacionesExtra().remove(participacionesExtra);
                partiextrarondaOld = em.merge(partiextrarondaOld);
            }
            if (partiextrarondaNew != null && !partiextrarondaNew.equals(partiextrarondaOld)) {
                partiextrarondaNew.getListaParticipacionesExtra().add(participacionesExtra);
                partiextrarondaNew = em.merge(partiextrarondaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = participacionesExtra.getId();
                if (findParticipacionesExtra(id) == null) {
                    throw new NonexistentEntityException("The participacionesExtra with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParticipacionesExtra participacionesExtra;
            try {
                participacionesExtra = em.getReference(ParticipacionesExtra.class, id);
                participacionesExtra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participacionesExtra with id " + id + " no longer exists.", enfe);
            }
            Participaciones parti = participacionesExtra.getParti();
            if (parti != null) {
                parti.getListaPartiExtra().remove(participacionesExtra);
                parti = em.merge(parti);
            }
            Ronda partiextraronda = participacionesExtra.getPartiextraronda();
            if (partiextraronda != null) {
                partiextraronda.getListaParticipacionesExtra().remove(participacionesExtra);
                partiextraronda = em.merge(partiextraronda);
            }
            em.remove(participacionesExtra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParticipacionesExtra> findParticipacionesExtraEntities() {
        return findParticipacionesExtraEntities(true, -1, -1);
    }

    public List<ParticipacionesExtra> findParticipacionesExtraEntities(int maxResults, int firstResult) {
        return findParticipacionesExtraEntities(false, maxResults, firstResult);
    }

    private List<ParticipacionesExtra> findParticipacionesExtraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParticipacionesExtra.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ParticipacionesExtra findParticipacionesExtra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParticipacionesExtra.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipacionesExtraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParticipacionesExtra> rt = cq.from(ParticipacionesExtra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
