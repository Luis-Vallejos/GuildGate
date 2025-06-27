package com.guildgate.web.Persistence;

import com.guildgate.web.Modelo.ImagenBanner;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import com.guildgate.web.Utilities.Enum;

/**
 *
 * @author Juan - Luis
 */
public class ImagenBannerJpaController extends AbstractJpaController implements Serializable {

    public void create(ImagenBanner imagenBanner) {
        if (imagenBanner.getListaJugadores() == null) {
            imagenBanner.setListaJugadores(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuarios> attachedListaJugadores = new ArrayList<Usuarios>();
            for (Usuarios listaJugadoresUsuariosToAttach : imagenBanner.getListaJugadores()) {
                listaJugadoresUsuariosToAttach = em.getReference(listaJugadoresUsuariosToAttach.getClass(), listaJugadoresUsuariosToAttach.getId());
                attachedListaJugadores.add(listaJugadoresUsuariosToAttach);
            }
            imagenBanner.setListaJugadores(attachedListaJugadores);
            em.persist(imagenBanner);
            for (Usuarios listaJugadoresUsuarios : imagenBanner.getListaJugadores()) {
                ImagenBanner oldImgBOfListaJugadoresUsuarios = listaJugadoresUsuarios.getImgB();
                listaJugadoresUsuarios.setImgB(imagenBanner);
                listaJugadoresUsuarios = em.merge(listaJugadoresUsuarios);
                if (oldImgBOfListaJugadoresUsuarios != null) {
                    oldImgBOfListaJugadoresUsuarios.getListaJugadores().remove(listaJugadoresUsuarios);
                    oldImgBOfListaJugadoresUsuarios = em.merge(oldImgBOfListaJugadoresUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagenBanner imagenBanner) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagenBanner persistentImagenBanner = em.find(ImagenBanner.class, imagenBanner.getId());
            List<Usuarios> listaJugadoresOld = persistentImagenBanner.getListaJugadores();
            List<Usuarios> listaJugadoresNew = imagenBanner.getListaJugadores();
            List<Usuarios> attachedListaJugadoresNew = new ArrayList<Usuarios>();
            for (Usuarios listaJugadoresNewUsuariosToAttach : listaJugadoresNew) {
                listaJugadoresNewUsuariosToAttach = em.getReference(listaJugadoresNewUsuariosToAttach.getClass(), listaJugadoresNewUsuariosToAttach.getId());
                attachedListaJugadoresNew.add(listaJugadoresNewUsuariosToAttach);
            }
            listaJugadoresNew = attachedListaJugadoresNew;
            imagenBanner.setListaJugadores(listaJugadoresNew);
            imagenBanner = em.merge(imagenBanner);
            for (Usuarios listaJugadoresOldUsuarios : listaJugadoresOld) {
                if (!listaJugadoresNew.contains(listaJugadoresOldUsuarios)) {
                    listaJugadoresOldUsuarios.setImgB(null);
                    listaJugadoresOldUsuarios = em.merge(listaJugadoresOldUsuarios);
                }
            }
            for (Usuarios listaJugadoresNewUsuarios : listaJugadoresNew) {
                if (!listaJugadoresOld.contains(listaJugadoresNewUsuarios)) {
                    ImagenBanner oldImgBOfListaJugadoresNewUsuarios = listaJugadoresNewUsuarios.getImgB();
                    listaJugadoresNewUsuarios.setImgB(imagenBanner);
                    listaJugadoresNewUsuarios = em.merge(listaJugadoresNewUsuarios);
                    if (oldImgBOfListaJugadoresNewUsuarios != null && !oldImgBOfListaJugadoresNewUsuarios.equals(imagenBanner)) {
                        oldImgBOfListaJugadoresNewUsuarios.getListaJugadores().remove(listaJugadoresNewUsuarios);
                        oldImgBOfListaJugadoresNewUsuarios = em.merge(oldImgBOfListaJugadoresNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = imagenBanner.getId();
                if (findImagenBanner(id) == null) {
                    throw new NonexistentEntityException("The imagenBanner with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagenBanner imagenBanner;
            try {
                imagenBanner = em.getReference(ImagenBanner.class, id);
                imagenBanner.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagenBanner with id " + id + " no longer exists.", enfe);
            }
            List<Usuarios> listaJugadores = imagenBanner.getListaJugadores();
            for (Usuarios listaJugadoresUsuarios : listaJugadores) {
                listaJugadoresUsuarios.setImgB(null);
                listaJugadoresUsuarios = em.merge(listaJugadoresUsuarios);
            }
            em.remove(imagenBanner);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagenBanner> findImagenBannerEntities() {
        return findImagenBannerEntities(true, -1, -1);
    }

    public List<ImagenBanner> findImagenBannerEntities(int maxResults, int firstResult) {
        return findImagenBannerEntities(false, maxResults, firstResult);
    }

    private List<ImagenBanner> findImagenBannerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagenBanner.class));
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

    public ImagenBanner findImagenBanner(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagenBanner.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagenBannerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagenBanner> rt = cq.from(ImagenBanner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ImagenBanner findBannerByNombre(String nombreBanner) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM ImagenBanner i WHERE i.nomArchivo = :nombreBanner");
            query.setParameter("nombreBanner", nombreBanner);
            List<ImagenBanner> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
        } finally {
            em.close();
        }
        return null;
    }

    public List<ImagenBanner> getPredeterminedBanners() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM ImagenBanner i WHERE i.origenArchivo = :origenArchivo");
            query.setParameter("origenArchivo", Enum.OrigenArchivo.PREDETERMINADA);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
