package com.guildgate.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class MundosJpaController extends AbstractJpaController implements Serializable {

    public void create(Mundos mundos) {
        if (mundos.getListaGremios() == null) {
            mundos.setListaGremios(new ArrayList<Gremio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Gremio> attachedListaGremios = new ArrayList<Gremio>();
            for (Gremio listaGremiosGremioToAttach : mundos.getListaGremios()) {
                listaGremiosGremioToAttach = em.getReference(listaGremiosGremioToAttach.getClass(), listaGremiosGremioToAttach.getId());
                attachedListaGremios.add(listaGremiosGremioToAttach);
            }
            mundos.setListaGremios(attachedListaGremios);
            em.persist(mundos);
            for (Gremio listaGremiosGremio : mundos.getListaGremios()) {
                Mundos oldGremiomundoOfListaGremiosGremio = listaGremiosGremio.getGremiomundo();
                listaGremiosGremio.setGremiomundo(mundos);
                listaGremiosGremio = em.merge(listaGremiosGremio);
                if (oldGremiomundoOfListaGremiosGremio != null) {
                    oldGremiomundoOfListaGremiosGremio.getListaGremios().remove(listaGremiosGremio);
                    oldGremiomundoOfListaGremiosGremio = em.merge(oldGremiomundoOfListaGremiosGremio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mundos mundos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mundos persistentMundos = em.find(Mundos.class, mundos.getId());
            List<Gremio> listaGremiosOld = persistentMundos.getListaGremios();
            List<Gremio> listaGremiosNew = mundos.getListaGremios();
            List<String> illegalOrphanMessages = null;
            for (Gremio listaGremiosOldGremio : listaGremiosOld) {
                if (!listaGremiosNew.contains(listaGremiosOldGremio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gremio " + listaGremiosOldGremio + " since its gremiomundo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Gremio> attachedListaGremiosNew = new ArrayList<Gremio>();
            for (Gremio listaGremiosNewGremioToAttach : listaGremiosNew) {
                listaGremiosNewGremioToAttach = em.getReference(listaGremiosNewGremioToAttach.getClass(), listaGremiosNewGremioToAttach.getId());
                attachedListaGremiosNew.add(listaGremiosNewGremioToAttach);
            }
            listaGremiosNew = attachedListaGremiosNew;
            mundos.setListaGremios(listaGremiosNew);
            mundos = em.merge(mundos);
            for (Gremio listaGremiosNewGremio : listaGremiosNew) {
                if (!listaGremiosOld.contains(listaGremiosNewGremio)) {
                    Mundos oldGremiomundoOfListaGremiosNewGremio = listaGremiosNewGremio.getGremiomundo();
                    listaGremiosNewGremio.setGremiomundo(mundos);
                    listaGremiosNewGremio = em.merge(listaGremiosNewGremio);
                    if (oldGremiomundoOfListaGremiosNewGremio != null && !oldGremiomundoOfListaGremiosNewGremio.equals(mundos)) {
                        oldGremiomundoOfListaGremiosNewGremio.getListaGremios().remove(listaGremiosNewGremio);
                        oldGremiomundoOfListaGremiosNewGremio = em.merge(oldGremiomundoOfListaGremiosNewGremio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mundos.getId();
                if (findMundos(id) == null) {
                    throw new NonexistentEntityException("The mundos with id " + id + " no longer exists.");
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
            Mundos mundos;
            try {
                mundos = em.getReference(Mundos.class, id);
                mundos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mundos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Gremio> listaGremiosOrphanCheck = mundos.getListaGremios();
            for (Gremio listaGremiosOrphanCheckGremio : listaGremiosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mundos (" + mundos + ") cannot be destroyed since the Gremio " + listaGremiosOrphanCheckGremio + " in its listaGremios field has a non-nullable gremiomundo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(mundos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mundos> findMundosEntities() {
        return findMundosEntities(true, -1, -1);
    }

    public List<Mundos> findMundosEntities(int maxResults, int firstResult) {
        return findMundosEntities(false, maxResults, firstResult);
    }

    private List<Mundos> findMundosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mundos.class));
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

    public Mundos findMundos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mundos.class, id);
        } finally {
            em.close();
        }
    }

    public int getMundosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mundos> rt = cq.from(Mundos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
