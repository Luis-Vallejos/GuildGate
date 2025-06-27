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
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Modelo.Bosses;
import com.guildgate.web.Modelo.Participaciones;
import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Modelo.Ronda;
import com.guildgate.web.Modelo.ParticipacionesExtra;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class ParticipacionesJpaController implements Serializable {

    public ParticipacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Participaciones participaciones) {
        if (participaciones.getListaPartiExtra() == null) {
            participaciones.setListaPartiExtra(new HashSet<ParticipacionesExtra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios userparti = participaciones.getUserparti();
            if (userparti != null) {
                userparti = em.getReference(userparti.getClass(), userparti.getId());
                participaciones.setUserparti(userparti);
            }
            Bosses boss = participaciones.getBoss();
            if (boss != null) {
                boss = em.getReference(boss.getClass(), boss.getId());
                participaciones.setBoss(boss);
            }
            Raid raidparti = participaciones.getRaidparti();
            if (raidparti != null) {
                raidparti = em.getReference(raidparti.getClass(), raidparti.getId());
                participaciones.setRaidparti(raidparti);
            }
            Ronda partironda = participaciones.getPartironda();
            if (partironda != null) {
                partironda = em.getReference(partironda.getClass(), partironda.getId());
                participaciones.setPartironda(partironda);
            }
            Set<ParticipacionesExtra> attachedListaPartiExtra = new HashSet<ParticipacionesExtra>();
            for (ParticipacionesExtra listaPartiExtraParticipacionesExtraToAttach : participaciones.getListaPartiExtra()) {
                listaPartiExtraParticipacionesExtraToAttach = em.getReference(listaPartiExtraParticipacionesExtraToAttach.getClass(), listaPartiExtraParticipacionesExtraToAttach.getId());
                attachedListaPartiExtra.add(listaPartiExtraParticipacionesExtraToAttach);
            }
            participaciones.setListaPartiExtra(attachedListaPartiExtra);
            em.persist(participaciones);
            if (userparti != null) {
                userparti.getListaParticipaciones().add(participaciones);
                userparti = em.merge(userparti);
            }
            if (boss != null) {
                boss.getListaParticipaciones().add(participaciones);
                boss = em.merge(boss);
            }
            if (raidparti != null) {
                raidparti.getListaParticipaciones().add(participaciones);
                raidparti = em.merge(raidparti);
            }
            if (partironda != null) {
                partironda.getListaParticipaciones().add(participaciones);
                partironda = em.merge(partironda);
            }
            for (ParticipacionesExtra listaPartiExtraParticipacionesExtra : participaciones.getListaPartiExtra()) {
                Participaciones oldPartiOfListaPartiExtraParticipacionesExtra = listaPartiExtraParticipacionesExtra.getParti();
                listaPartiExtraParticipacionesExtra.setParti(participaciones);
                listaPartiExtraParticipacionesExtra = em.merge(listaPartiExtraParticipacionesExtra);
                if (oldPartiOfListaPartiExtraParticipacionesExtra != null) {
                    oldPartiOfListaPartiExtraParticipacionesExtra.getListaPartiExtra().remove(listaPartiExtraParticipacionesExtra);
                    oldPartiOfListaPartiExtraParticipacionesExtra = em.merge(oldPartiOfListaPartiExtraParticipacionesExtra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Participaciones participaciones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Participaciones persistentParticipaciones = em.find(Participaciones.class, participaciones.getId());
            Usuarios userpartiOld = persistentParticipaciones.getUserparti();
            Usuarios userpartiNew = participaciones.getUserparti();
            Bosses bossOld = persistentParticipaciones.getBoss();
            Bosses bossNew = participaciones.getBoss();
            Raid raidpartiOld = persistentParticipaciones.getRaidparti();
            Raid raidpartiNew = participaciones.getRaidparti();
            Ronda partirondaOld = persistentParticipaciones.getPartironda();
            Ronda partirondaNew = participaciones.getPartironda();
            Set<ParticipacionesExtra> listaPartiExtraOld = persistentParticipaciones.getListaPartiExtra();
            Set<ParticipacionesExtra> listaPartiExtraNew = participaciones.getListaPartiExtra();
            List<String> illegalOrphanMessages = null;
            for (ParticipacionesExtra listaPartiExtraOldParticipacionesExtra : listaPartiExtraOld) {
                if (!listaPartiExtraNew.contains(listaPartiExtraOldParticipacionesExtra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipacionesExtra " + listaPartiExtraOldParticipacionesExtra + " since its parti field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userpartiNew != null) {
                userpartiNew = em.getReference(userpartiNew.getClass(), userpartiNew.getId());
                participaciones.setUserparti(userpartiNew);
            }
            if (bossNew != null) {
                bossNew = em.getReference(bossNew.getClass(), bossNew.getId());
                participaciones.setBoss(bossNew);
            }
            if (raidpartiNew != null) {
                raidpartiNew = em.getReference(raidpartiNew.getClass(), raidpartiNew.getId());
                participaciones.setRaidparti(raidpartiNew);
            }
            if (partirondaNew != null) {
                partirondaNew = em.getReference(partirondaNew.getClass(), partirondaNew.getId());
                participaciones.setPartironda(partirondaNew);
            }
            Set<ParticipacionesExtra> attachedListaPartiExtraNew = new HashSet<ParticipacionesExtra>();
            for (ParticipacionesExtra listaPartiExtraNewParticipacionesExtraToAttach : listaPartiExtraNew) {
                listaPartiExtraNewParticipacionesExtraToAttach = em.getReference(listaPartiExtraNewParticipacionesExtraToAttach.getClass(), listaPartiExtraNewParticipacionesExtraToAttach.getId());
                attachedListaPartiExtraNew.add(listaPartiExtraNewParticipacionesExtraToAttach);
            }
            listaPartiExtraNew = attachedListaPartiExtraNew;
            participaciones.setListaPartiExtra(listaPartiExtraNew);
            participaciones = em.merge(participaciones);
            if (userpartiOld != null && !userpartiOld.equals(userpartiNew)) {
                userpartiOld.getListaParticipaciones().remove(participaciones);
                userpartiOld = em.merge(userpartiOld);
            }
            if (userpartiNew != null && !userpartiNew.equals(userpartiOld)) {
                userpartiNew.getListaParticipaciones().add(participaciones);
                userpartiNew = em.merge(userpartiNew);
            }
            if (bossOld != null && !bossOld.equals(bossNew)) {
                bossOld.getListaParticipaciones().remove(participaciones);
                bossOld = em.merge(bossOld);
            }
            if (bossNew != null && !bossNew.equals(bossOld)) {
                bossNew.getListaParticipaciones().add(participaciones);
                bossNew = em.merge(bossNew);
            }
            if (raidpartiOld != null && !raidpartiOld.equals(raidpartiNew)) {
                raidpartiOld.getListaParticipaciones().remove(participaciones);
                raidpartiOld = em.merge(raidpartiOld);
            }
            if (raidpartiNew != null && !raidpartiNew.equals(raidpartiOld)) {
                raidpartiNew.getListaParticipaciones().add(participaciones);
                raidpartiNew = em.merge(raidpartiNew);
            }
            if (partirondaOld != null && !partirondaOld.equals(partirondaNew)) {
                partirondaOld.getListaParticipaciones().remove(participaciones);
                partirondaOld = em.merge(partirondaOld);
            }
            if (partirondaNew != null && !partirondaNew.equals(partirondaOld)) {
                partirondaNew.getListaParticipaciones().add(participaciones);
                partirondaNew = em.merge(partirondaNew);
            }
            for (ParticipacionesExtra listaPartiExtraNewParticipacionesExtra : listaPartiExtraNew) {
                if (!listaPartiExtraOld.contains(listaPartiExtraNewParticipacionesExtra)) {
                    Participaciones oldPartiOfListaPartiExtraNewParticipacionesExtra = listaPartiExtraNewParticipacionesExtra.getParti();
                    listaPartiExtraNewParticipacionesExtra.setParti(participaciones);
                    listaPartiExtraNewParticipacionesExtra = em.merge(listaPartiExtraNewParticipacionesExtra);
                    if (oldPartiOfListaPartiExtraNewParticipacionesExtra != null && !oldPartiOfListaPartiExtraNewParticipacionesExtra.equals(participaciones)) {
                        oldPartiOfListaPartiExtraNewParticipacionesExtra.getListaPartiExtra().remove(listaPartiExtraNewParticipacionesExtra);
                        oldPartiOfListaPartiExtraNewParticipacionesExtra = em.merge(oldPartiOfListaPartiExtraNewParticipacionesExtra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = participaciones.getId();
                if (findParticipaciones(id) == null) {
                    throw new NonexistentEntityException("The participaciones with id " + id + " no longer exists.");
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
            Participaciones participaciones;
            try {
                participaciones = em.getReference(Participaciones.class, id);
                participaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participaciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<ParticipacionesExtra> listaPartiExtraOrphanCheck = participaciones.getListaPartiExtra();
            for (ParticipacionesExtra listaPartiExtraOrphanCheckParticipacionesExtra : listaPartiExtraOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participaciones (" + participaciones + ") cannot be destroyed since the ParticipacionesExtra " + listaPartiExtraOrphanCheckParticipacionesExtra + " in its listaPartiExtra field has a non-nullable parti field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios userparti = participaciones.getUserparti();
            if (userparti != null) {
                userparti.getListaParticipaciones().remove(participaciones);
                userparti = em.merge(userparti);
            }
            Bosses boss = participaciones.getBoss();
            if (boss != null) {
                boss.getListaParticipaciones().remove(participaciones);
                boss = em.merge(boss);
            }
            Raid raidparti = participaciones.getRaidparti();
            if (raidparti != null) {
                raidparti.getListaParticipaciones().remove(participaciones);
                raidparti = em.merge(raidparti);
            }
            Ronda partironda = participaciones.getPartironda();
            if (partironda != null) {
                partironda.getListaParticipaciones().remove(participaciones);
                partironda = em.merge(partironda);
            }
            em.remove(participaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Participaciones> findParticipacionesEntities() {
        return findParticipacionesEntities(true, -1, -1);
    }

    public List<Participaciones> findParticipacionesEntities(int maxResults, int firstResult) {
        return findParticipacionesEntities(false, maxResults, firstResult);
    }

    private List<Participaciones> findParticipacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Participaciones.class));
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

    public Participaciones findParticipaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Participaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Participaciones> rt = cq.from(Participaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
