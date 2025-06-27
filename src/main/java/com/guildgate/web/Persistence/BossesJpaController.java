/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guildgate.web.Persistence;

import com.guildgate.web.Modelo.Bosses;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Modelo.Participaciones;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class BossesJpaController implements Serializable {

    public BossesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bosses bosses) {
        if (bosses.getListaParticipaciones() == null) {
            bosses.setListaParticipaciones(new ArrayList<Participaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Raid raidboss = bosses.getRaidboss();
            if (raidboss != null) {
                raidboss = em.getReference(raidboss.getClass(), raidboss.getId());
                bosses.setRaidboss(raidboss);
            }
            List<Participaciones> attachedListaParticipaciones = new ArrayList<Participaciones>();
            for (Participaciones listaParticipacionesParticipacionesToAttach : bosses.getListaParticipaciones()) {
                listaParticipacionesParticipacionesToAttach = em.getReference(listaParticipacionesParticipacionesToAttach.getClass(), listaParticipacionesParticipacionesToAttach.getId());
                attachedListaParticipaciones.add(listaParticipacionesParticipacionesToAttach);
            }
            bosses.setListaParticipaciones(attachedListaParticipaciones);
            em.persist(bosses);
            if (raidboss != null) {
                raidboss.getListaBosses().add(bosses);
                raidboss = em.merge(raidboss);
            }
            for (Participaciones listaParticipacionesParticipaciones : bosses.getListaParticipaciones()) {
                Bosses oldBossOfListaParticipacionesParticipaciones = listaParticipacionesParticipaciones.getBoss();
                listaParticipacionesParticipaciones.setBoss(bosses);
                listaParticipacionesParticipaciones = em.merge(listaParticipacionesParticipaciones);
                if (oldBossOfListaParticipacionesParticipaciones != null) {
                    oldBossOfListaParticipacionesParticipaciones.getListaParticipaciones().remove(listaParticipacionesParticipaciones);
                    oldBossOfListaParticipacionesParticipaciones = em.merge(oldBossOfListaParticipacionesParticipaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bosses bosses) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bosses persistentBosses = em.find(Bosses.class, bosses.getId());
            Raid raidbossOld = persistentBosses.getRaidboss();
            Raid raidbossNew = bosses.getRaidboss();
            List<Participaciones> listaParticipacionesOld = persistentBosses.getListaParticipaciones();
            List<Participaciones> listaParticipacionesNew = bosses.getListaParticipaciones();
            List<String> illegalOrphanMessages = null;
            for (Participaciones listaParticipacionesOldParticipaciones : listaParticipacionesOld) {
                if (!listaParticipacionesNew.contains(listaParticipacionesOldParticipaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participaciones " + listaParticipacionesOldParticipaciones + " since its boss field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (raidbossNew != null) {
                raidbossNew = em.getReference(raidbossNew.getClass(), raidbossNew.getId());
                bosses.setRaidboss(raidbossNew);
            }
            List<Participaciones> attachedListaParticipacionesNew = new ArrayList<Participaciones>();
            for (Participaciones listaParticipacionesNewParticipacionesToAttach : listaParticipacionesNew) {
                listaParticipacionesNewParticipacionesToAttach = em.getReference(listaParticipacionesNewParticipacionesToAttach.getClass(), listaParticipacionesNewParticipacionesToAttach.getId());
                attachedListaParticipacionesNew.add(listaParticipacionesNewParticipacionesToAttach);
            }
            listaParticipacionesNew = attachedListaParticipacionesNew;
            bosses.setListaParticipaciones(listaParticipacionesNew);
            bosses = em.merge(bosses);
            if (raidbossOld != null && !raidbossOld.equals(raidbossNew)) {
                raidbossOld.getListaBosses().remove(bosses);
                raidbossOld = em.merge(raidbossOld);
            }
            if (raidbossNew != null && !raidbossNew.equals(raidbossOld)) {
                raidbossNew.getListaBosses().add(bosses);
                raidbossNew = em.merge(raidbossNew);
            }
            for (Participaciones listaParticipacionesNewParticipaciones : listaParticipacionesNew) {
                if (!listaParticipacionesOld.contains(listaParticipacionesNewParticipaciones)) {
                    Bosses oldBossOfListaParticipacionesNewParticipaciones = listaParticipacionesNewParticipaciones.getBoss();
                    listaParticipacionesNewParticipaciones.setBoss(bosses);
                    listaParticipacionesNewParticipaciones = em.merge(listaParticipacionesNewParticipaciones);
                    if (oldBossOfListaParticipacionesNewParticipaciones != null && !oldBossOfListaParticipacionesNewParticipaciones.equals(bosses)) {
                        oldBossOfListaParticipacionesNewParticipaciones.getListaParticipaciones().remove(listaParticipacionesNewParticipaciones);
                        oldBossOfListaParticipacionesNewParticipaciones = em.merge(oldBossOfListaParticipacionesNewParticipaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bosses.getId();
                if (findBosses(id) == null) {
                    throw new NonexistentEntityException("The bosses with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bosses bosses;
            try {
                bosses = em.getReference(Bosses.class, id);
                bosses.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bosses with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Participaciones> listaParticipacionesOrphanCheck = bosses.getListaParticipaciones();
            for (Participaciones listaParticipacionesOrphanCheckParticipaciones : listaParticipacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bosses (" + bosses + ") cannot be destroyed since the Participaciones " + listaParticipacionesOrphanCheckParticipaciones + " in its listaParticipaciones field has a non-nullable boss field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Raid raidboss = bosses.getRaidboss();
            if (raidboss != null) {
                raidboss.getListaBosses().remove(bosses);
                raidboss = em.merge(raidboss);
            }
            em.remove(bosses);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bosses> findBossesEntities() {
        return findBossesEntities(true, -1, -1);
    }

    public List<Bosses> findBossesEntities(int maxResults, int firstResult) {
        return findBossesEntities(false, maxResults, firstResult);
    }

    private List<Bosses> findBossesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bosses.class));
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

    public Bosses findBosses(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bosses.class, id);
        } finally {
            em.close();
        }
    }

    public int getBossesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bosses> rt = cq.from(Bosses.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
