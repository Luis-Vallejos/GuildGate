package com.guildgate.web.Persistence;

import com.guildgate.web.Modelo.AvatarGremio;
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
public class AvatarGremioJpaController extends AbstractJpaController implements Serializable {

    public void create(AvatarGremio avatarGremio) {
        if (avatarGremio.getListaGremios() == null) {
            avatarGremio.setListaGremios(new ArrayList<Gremio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Gremio> attachedListaGremios = new ArrayList<Gremio>();
            for (Gremio listaGremiosGremioToAttach : avatarGremio.getListaGremios()) {
                listaGremiosGremioToAttach = em.getReference(listaGremiosGremioToAttach.getClass(), listaGremiosGremioToAttach.getId());
                attachedListaGremios.add(listaGremiosGremioToAttach);
            }
            avatarGremio.setListaGremios(attachedListaGremios);
            em.persist(avatarGremio);
            for (Gremio listaGremiosGremio : avatarGremio.getListaGremios()) {
                AvatarGremio oldImgOfListaGremiosGremio = listaGremiosGremio.getImg();
                listaGremiosGremio.setImg(avatarGremio);
                listaGremiosGremio = em.merge(listaGremiosGremio);
                if (oldImgOfListaGremiosGremio != null) {
                    oldImgOfListaGremiosGremio.getListaGremios().remove(listaGremiosGremio);
                    oldImgOfListaGremiosGremio = em.merge(oldImgOfListaGremiosGremio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvatarGremio avatarGremio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvatarGremio persistentAvatarGremio = em.find(AvatarGremio.class, avatarGremio.getId());
            List<Gremio> listaGremiosOld = persistentAvatarGremio.getListaGremios();
            List<Gremio> listaGremiosNew = avatarGremio.getListaGremios();
            List<String> illegalOrphanMessages = null;
            for (Gremio listaGremiosOldGremio : listaGremiosOld) {
                if (!listaGremiosNew.contains(listaGremiosOldGremio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gremio " + listaGremiosOldGremio + " since its img field is not nullable.");
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
            avatarGremio.setListaGremios(listaGremiosNew);
            avatarGremio = em.merge(avatarGremio);
            for (Gremio listaGremiosNewGremio : listaGremiosNew) {
                if (!listaGremiosOld.contains(listaGremiosNewGremio)) {
                    AvatarGremio oldImgOfListaGremiosNewGremio = listaGremiosNewGremio.getImg();
                    listaGremiosNewGremio.setImg(avatarGremio);
                    listaGremiosNewGremio = em.merge(listaGremiosNewGremio);
                    if (oldImgOfListaGremiosNewGremio != null && !oldImgOfListaGremiosNewGremio.equals(avatarGremio)) {
                        oldImgOfListaGremiosNewGremio.getListaGremios().remove(listaGremiosNewGremio);
                        oldImgOfListaGremiosNewGremio = em.merge(oldImgOfListaGremiosNewGremio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = avatarGremio.getId();
                if (findAvatarGremio(id) == null) {
                    throw new NonexistentEntityException("The avatarGremio with id " + id + " no longer exists.");
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
            AvatarGremio avatarGremio;
            try {
                avatarGremio = em.getReference(AvatarGremio.class, id);
                avatarGremio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avatarGremio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Gremio> listaGremiosOrphanCheck = avatarGremio.getListaGremios();
            for (Gremio listaGremiosOrphanCheckGremio : listaGremiosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvatarGremio (" + avatarGremio + ") cannot be destroyed since the Gremio " + listaGremiosOrphanCheckGremio + " in its listaGremios field has a non-nullable img field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(avatarGremio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvatarGremio> findAvatarGremioEntities() {
        return findAvatarGremioEntities(true, -1, -1);
    }

    public List<AvatarGremio> findAvatarGremioEntities(int maxResults, int firstResult) {
        return findAvatarGremioEntities(false, maxResults, firstResult);
    }

    private List<AvatarGremio> findAvatarGremioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvatarGremio.class));
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

    public AvatarGremio findAvatarGremio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvatarGremio.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvatarGremioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvatarGremio> rt = cq.from(AvatarGremio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public AvatarGremio findImagenByNombre(String nombreArchivo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM AvatarGremio i WHERE i.nomArchivo = :nombreArchivo");
            query.setParameter("nombreArchivo", nombreArchivo);
            List<AvatarGremio> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
        } finally {
            em.close();
        }
        return null;
    }

    public List<AvatarGremio> getPredeterminedAvatarGremio() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM AvatarGremio i WHERE i.origenArchivo = :origenArchivo");
            query.setParameter("origenArchivo", Enum.OrigenArchivo.PREDETERMINADA);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
