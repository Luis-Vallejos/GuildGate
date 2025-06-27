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
import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Modelo.Participaciones;
import java.util.HashSet;
import java.util.Set;
import com.guildgate.web.Modelo.ParticipacionesExtra;
import com.guildgate.web.Modelo.Ronda;
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
public class RondaJpaController implements Serializable {

    public RondaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ronda ronda) {
        if (ronda.getListaParticipaciones() == null) {
            ronda.setListaParticipaciones(new HashSet<Participaciones>());
        }
        if (ronda.getListaParticipacionesExtra() == null) {
            ronda.setListaParticipacionesExtra(new HashSet<ParticipacionesExtra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Raid raidronda = ronda.getRaidronda();
            if (raidronda != null) {
                raidronda = em.getReference(raidronda.getClass(), raidronda.getId());
                ronda.setRaidronda(raidronda);
            }
            Set<Participaciones> attachedListaParticipaciones = new HashSet<Participaciones>();
            for (Participaciones listaParticipacionesParticipacionesToAttach : ronda.getListaParticipaciones()) {
                listaParticipacionesParticipacionesToAttach = em.getReference(listaParticipacionesParticipacionesToAttach.getClass(), listaParticipacionesParticipacionesToAttach.getId());
                attachedListaParticipaciones.add(listaParticipacionesParticipacionesToAttach);
            }
            ronda.setListaParticipaciones(attachedListaParticipaciones);
            Set<ParticipacionesExtra> attachedListaParticipacionesExtra = new HashSet<ParticipacionesExtra>();
            for (ParticipacionesExtra listaParticipacionesExtraParticipacionesExtraToAttach : ronda.getListaParticipacionesExtra()) {
                listaParticipacionesExtraParticipacionesExtraToAttach = em.getReference(listaParticipacionesExtraParticipacionesExtraToAttach.getClass(), listaParticipacionesExtraParticipacionesExtraToAttach.getId());
                attachedListaParticipacionesExtra.add(listaParticipacionesExtraParticipacionesExtraToAttach);
            }
            ronda.setListaParticipacionesExtra(attachedListaParticipacionesExtra);
            em.persist(ronda);
            if (raidronda != null) {
                raidronda.getListaRondas().add(ronda);
                raidronda = em.merge(raidronda);
            }
            for (Participaciones listaParticipacionesParticipaciones : ronda.getListaParticipaciones()) {
                Ronda oldPartirondaOfListaParticipacionesParticipaciones = listaParticipacionesParticipaciones.getPartironda();
                listaParticipacionesParticipaciones.setPartironda(ronda);
                listaParticipacionesParticipaciones = em.merge(listaParticipacionesParticipaciones);
                if (oldPartirondaOfListaParticipacionesParticipaciones != null) {
                    oldPartirondaOfListaParticipacionesParticipaciones.getListaParticipaciones().remove(listaParticipacionesParticipaciones);
                    oldPartirondaOfListaParticipacionesParticipaciones = em.merge(oldPartirondaOfListaParticipacionesParticipaciones);
                }
            }
            for (ParticipacionesExtra listaParticipacionesExtraParticipacionesExtra : ronda.getListaParticipacionesExtra()) {
                Ronda oldPartiextrarondaOfListaParticipacionesExtraParticipacionesExtra = listaParticipacionesExtraParticipacionesExtra.getPartiextraronda();
                listaParticipacionesExtraParticipacionesExtra.setPartiextraronda(ronda);
                listaParticipacionesExtraParticipacionesExtra = em.merge(listaParticipacionesExtraParticipacionesExtra);
                if (oldPartiextrarondaOfListaParticipacionesExtraParticipacionesExtra != null) {
                    oldPartiextrarondaOfListaParticipacionesExtraParticipacionesExtra.getListaParticipacionesExtra().remove(listaParticipacionesExtraParticipacionesExtra);
                    oldPartiextrarondaOfListaParticipacionesExtraParticipacionesExtra = em.merge(oldPartiextrarondaOfListaParticipacionesExtraParticipacionesExtra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ronda ronda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ronda persistentRonda = em.find(Ronda.class, ronda.getId());
            Raid raidrondaOld = persistentRonda.getRaidronda();
            Raid raidrondaNew = ronda.getRaidronda();
            Set<Participaciones> listaParticipacionesOld = persistentRonda.getListaParticipaciones();
            Set<Participaciones> listaParticipacionesNew = ronda.getListaParticipaciones();
            Set<ParticipacionesExtra> listaParticipacionesExtraOld = persistentRonda.getListaParticipacionesExtra();
            Set<ParticipacionesExtra> listaParticipacionesExtraNew = ronda.getListaParticipacionesExtra();
            List<String> illegalOrphanMessages = null;
            for (Participaciones listaParticipacionesOldParticipaciones : listaParticipacionesOld) {
                if (!listaParticipacionesNew.contains(listaParticipacionesOldParticipaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participaciones " + listaParticipacionesOldParticipaciones + " since its partironda field is not nullable.");
                }
            }
            for (ParticipacionesExtra listaParticipacionesExtraOldParticipacionesExtra : listaParticipacionesExtraOld) {
                if (!listaParticipacionesExtraNew.contains(listaParticipacionesExtraOldParticipacionesExtra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipacionesExtra " + listaParticipacionesExtraOldParticipacionesExtra + " since its partiextraronda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (raidrondaNew != null) {
                raidrondaNew = em.getReference(raidrondaNew.getClass(), raidrondaNew.getId());
                ronda.setRaidronda(raidrondaNew);
            }
            Set<Participaciones> attachedListaParticipacionesNew = new HashSet<Participaciones>();
            for (Participaciones listaParticipacionesNewParticipacionesToAttach : listaParticipacionesNew) {
                listaParticipacionesNewParticipacionesToAttach = em.getReference(listaParticipacionesNewParticipacionesToAttach.getClass(), listaParticipacionesNewParticipacionesToAttach.getId());
                attachedListaParticipacionesNew.add(listaParticipacionesNewParticipacionesToAttach);
            }
            listaParticipacionesNew = attachedListaParticipacionesNew;
            ronda.setListaParticipaciones(listaParticipacionesNew);
            Set<ParticipacionesExtra> attachedListaParticipacionesExtraNew = new HashSet<ParticipacionesExtra>();
            for (ParticipacionesExtra listaParticipacionesExtraNewParticipacionesExtraToAttach : listaParticipacionesExtraNew) {
                listaParticipacionesExtraNewParticipacionesExtraToAttach = em.getReference(listaParticipacionesExtraNewParticipacionesExtraToAttach.getClass(), listaParticipacionesExtraNewParticipacionesExtraToAttach.getId());
                attachedListaParticipacionesExtraNew.add(listaParticipacionesExtraNewParticipacionesExtraToAttach);
            }
            listaParticipacionesExtraNew = attachedListaParticipacionesExtraNew;
            ronda.setListaParticipacionesExtra(listaParticipacionesExtraNew);
            ronda = em.merge(ronda);
            if (raidrondaOld != null && !raidrondaOld.equals(raidrondaNew)) {
                raidrondaOld.getListaRondas().remove(ronda);
                raidrondaOld = em.merge(raidrondaOld);
            }
            if (raidrondaNew != null && !raidrondaNew.equals(raidrondaOld)) {
                raidrondaNew.getListaRondas().add(ronda);
                raidrondaNew = em.merge(raidrondaNew);
            }
            for (Participaciones listaParticipacionesNewParticipaciones : listaParticipacionesNew) {
                if (!listaParticipacionesOld.contains(listaParticipacionesNewParticipaciones)) {
                    Ronda oldPartirondaOfListaParticipacionesNewParticipaciones = listaParticipacionesNewParticipaciones.getPartironda();
                    listaParticipacionesNewParticipaciones.setPartironda(ronda);
                    listaParticipacionesNewParticipaciones = em.merge(listaParticipacionesNewParticipaciones);
                    if (oldPartirondaOfListaParticipacionesNewParticipaciones != null && !oldPartirondaOfListaParticipacionesNewParticipaciones.equals(ronda)) {
                        oldPartirondaOfListaParticipacionesNewParticipaciones.getListaParticipaciones().remove(listaParticipacionesNewParticipaciones);
                        oldPartirondaOfListaParticipacionesNewParticipaciones = em.merge(oldPartirondaOfListaParticipacionesNewParticipaciones);
                    }
                }
            }
            for (ParticipacionesExtra listaParticipacionesExtraNewParticipacionesExtra : listaParticipacionesExtraNew) {
                if (!listaParticipacionesExtraOld.contains(listaParticipacionesExtraNewParticipacionesExtra)) {
                    Ronda oldPartiextrarondaOfListaParticipacionesExtraNewParticipacionesExtra = listaParticipacionesExtraNewParticipacionesExtra.getPartiextraronda();
                    listaParticipacionesExtraNewParticipacionesExtra.setPartiextraronda(ronda);
                    listaParticipacionesExtraNewParticipacionesExtra = em.merge(listaParticipacionesExtraNewParticipacionesExtra);
                    if (oldPartiextrarondaOfListaParticipacionesExtraNewParticipacionesExtra != null && !oldPartiextrarondaOfListaParticipacionesExtraNewParticipacionesExtra.equals(ronda)) {
                        oldPartiextrarondaOfListaParticipacionesExtraNewParticipacionesExtra.getListaParticipacionesExtra().remove(listaParticipacionesExtraNewParticipacionesExtra);
                        oldPartiextrarondaOfListaParticipacionesExtraNewParticipacionesExtra = em.merge(oldPartiextrarondaOfListaParticipacionesExtraNewParticipacionesExtra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ronda.getId();
                if (findRonda(id) == null) {
                    throw new NonexistentEntityException("The ronda with id " + id + " no longer exists.");
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
            Ronda ronda;
            try {
                ronda = em.getReference(Ronda.class, id);
                ronda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ronda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Participaciones> listaParticipacionesOrphanCheck = ronda.getListaParticipaciones();
            for (Participaciones listaParticipacionesOrphanCheckParticipaciones : listaParticipacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ronda (" + ronda + ") cannot be destroyed since the Participaciones " + listaParticipacionesOrphanCheckParticipaciones + " in its listaParticipaciones field has a non-nullable partironda field.");
            }
            Set<ParticipacionesExtra> listaParticipacionesExtraOrphanCheck = ronda.getListaParticipacionesExtra();
            for (ParticipacionesExtra listaParticipacionesExtraOrphanCheckParticipacionesExtra : listaParticipacionesExtraOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ronda (" + ronda + ") cannot be destroyed since the ParticipacionesExtra " + listaParticipacionesExtraOrphanCheckParticipacionesExtra + " in its listaParticipacionesExtra field has a non-nullable partiextraronda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Raid raidronda = ronda.getRaidronda();
            if (raidronda != null) {
                raidronda.getListaRondas().remove(ronda);
                raidronda = em.merge(raidronda);
            }
            em.remove(ronda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ronda> findRondaEntities() {
        return findRondaEntities(true, -1, -1);
    }

    public List<Ronda> findRondaEntities(int maxResults, int firstResult) {
        return findRondaEntities(false, maxResults, firstResult);
    }

    private List<Ronda> findRondaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ronda.class));
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

    public Ronda findRonda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ronda.class, id);
        } finally {
            em.close();
        }
    }

    public int getRondaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ronda> rt = cq.from(Ronda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
