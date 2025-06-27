package com.guildgate.web.Persistence;

import com.guildgate.web.Modelo.FondoGremio;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import com.guildgate.web.Utilities.Enum;

/**
 *
 * @author Juan - Luis
 */
public class FondoGremioJpaController extends AbstractJpaController implements Serializable {

    public void create(FondoGremio fondoGremio) {
        if (fondoGremio.getListaGremios() == null) {
            fondoGremio.setListaGremios(new ArrayList<Gremio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Gremio> attachedListaGremios = new ArrayList<Gremio>();
            for (Gremio listaGremiosGremioToAttach : fondoGremio.getListaGremios()) {
                listaGremiosGremioToAttach = em.getReference(listaGremiosGremioToAttach.getClass(), listaGremiosGremioToAttach.getId());
                attachedListaGremios.add(listaGremiosGremioToAttach);
            }
            fondoGremio.setListaGremios(attachedListaGremios);
            em.persist(fondoGremio);
            for (Gremio listaGremiosGremio : fondoGremio.getListaGremios()) {
                FondoGremio oldImgFOfListaGremiosGremio = listaGremiosGremio.getImgF();
                listaGremiosGremio.setImgF(fondoGremio);
                listaGremiosGremio = em.merge(listaGremiosGremio);
                if (oldImgFOfListaGremiosGremio != null) {
                    oldImgFOfListaGremiosGremio.getListaGremios().remove(listaGremiosGremio);
                    oldImgFOfListaGremiosGremio = em.merge(oldImgFOfListaGremiosGremio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FondoGremio fondoGremio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FondoGremio persistentFondoGremio = em.find(FondoGremio.class, fondoGremio.getId());
            List<Gremio> listaGremiosOld = persistentFondoGremio.getListaGremios();
            List<Gremio> listaGremiosNew = fondoGremio.getListaGremios();
            List<String> illegalOrphanMessages = null;
            for (Gremio listaGremiosOldGremio : listaGremiosOld) {
                if (!listaGremiosNew.contains(listaGremiosOldGremio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gremio " + listaGremiosOldGremio + " since its imgF field is not nullable.");
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
            fondoGremio.setListaGremios(listaGremiosNew);
            fondoGremio = em.merge(fondoGremio);
            for (Gremio listaGremiosNewGremio : listaGremiosNew) {
                if (!listaGremiosOld.contains(listaGremiosNewGremio)) {
                    FondoGremio oldImgFOfListaGremiosNewGremio = listaGremiosNewGremio.getImgF();
                    listaGremiosNewGremio.setImgF(fondoGremio);
                    listaGremiosNewGremio = em.merge(listaGremiosNewGremio);
                    if (oldImgFOfListaGremiosNewGremio != null && !oldImgFOfListaGremiosNewGremio.equals(fondoGremio)) {
                        oldImgFOfListaGremiosNewGremio.getListaGremios().remove(listaGremiosNewGremio);
                        oldImgFOfListaGremiosNewGremio = em.merge(oldImgFOfListaGremiosNewGremio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = fondoGremio.getId();
                if (findFondoGremio(id) == null) {
                    throw new NonexistentEntityException("The fondoGremio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FondoGremio fondoGremio;
            try {
                fondoGremio = em.getReference(FondoGremio.class, id);
                fondoGremio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fondoGremio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Gremio> listaGremiosOrphanCheck = fondoGremio.getListaGremios();
            for (Gremio listaGremiosOrphanCheckGremio : listaGremiosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FondoGremio (" + fondoGremio + ") cannot be destroyed since the Gremio " + listaGremiosOrphanCheckGremio + " in its listaGremios field has a non-nullable imgF field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fondoGremio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FondoGremio> findFondoGremioEntities() {
        return findFondoGremioEntities(true, -1, -1);
    }

    public List<FondoGremio> findFondoGremioEntities(int maxResults, int firstResult) {
        return findFondoGremioEntities(false, maxResults, firstResult);
    }

    private List<FondoGremio> findFondoGremioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FondoGremio.class));
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

    public FondoGremio findFondoGremio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FondoGremio.class, id);
        } finally {
            em.close();
        }
    }

    public int getFondoGremioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FondoGremio> rt = cq.from(FondoGremio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public FondoGremio findImagenByNombre(String nombreArchivo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM FondoGremio i WHERE i.nomArchivo = :nombreArchivo");
            query.setParameter("nombreArchivo", nombreArchivo);
            List<FondoGremio> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
        } finally {
            em.close();
        }
        return null;
    }

    public List<FondoGremio> getPredeterminedFondoGremio() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM FondoGremio i WHERE i.origenArchivo = :origenArchivo");
            query.setParameter("origenArchivo", Enum.OrigenArchivo.PREDETERMINADA);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
