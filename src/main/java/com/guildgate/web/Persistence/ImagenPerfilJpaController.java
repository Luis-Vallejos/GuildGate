package com.guildgate.web.Persistence;

import com.guildgate.web.Modelo.ImagenPerfil;
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
public class ImagenPerfilJpaController extends AbstractJpaController implements Serializable {

    public void create(ImagenPerfil imagenPerfil) {
        if (imagenPerfil.getListaUsuarios() == null) {
            imagenPerfil.setListaUsuarios(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuarios> attachedListaUsuarios = new ArrayList<Usuarios>();
            for (Usuarios listaUsuariosUsuariosToAttach : imagenPerfil.getListaUsuarios()) {
                listaUsuariosUsuariosToAttach = em.getReference(listaUsuariosUsuariosToAttach.getClass(), listaUsuariosUsuariosToAttach.getId());
                attachedListaUsuarios.add(listaUsuariosUsuariosToAttach);
            }
            imagenPerfil.setListaUsuarios(attachedListaUsuarios);
            em.persist(imagenPerfil);
            for (Usuarios listaUsuariosUsuarios : imagenPerfil.getListaUsuarios()) {
                ImagenPerfil oldImgOfListaUsuariosUsuarios = listaUsuariosUsuarios.getImg();
                listaUsuariosUsuarios.setImg(imagenPerfil);
                listaUsuariosUsuarios = em.merge(listaUsuariosUsuarios);
                if (oldImgOfListaUsuariosUsuarios != null) {
                    oldImgOfListaUsuariosUsuarios.getListaUsuarios().remove(listaUsuariosUsuarios);
                    oldImgOfListaUsuariosUsuarios = em.merge(oldImgOfListaUsuariosUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagenPerfil imagenPerfil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagenPerfil persistentImagenPerfil = em.find(ImagenPerfil.class, imagenPerfil.getId());
            List<Usuarios> listaUsuariosOld = persistentImagenPerfil.getListaUsuarios();
            List<Usuarios> listaUsuariosNew = imagenPerfil.getListaUsuarios();
            List<Usuarios> attachedListaUsuariosNew = new ArrayList<Usuarios>();
            for (Usuarios listaUsuariosNewUsuariosToAttach : listaUsuariosNew) {
                listaUsuariosNewUsuariosToAttach = em.getReference(listaUsuariosNewUsuariosToAttach.getClass(), listaUsuariosNewUsuariosToAttach.getId());
                attachedListaUsuariosNew.add(listaUsuariosNewUsuariosToAttach);
            }
            listaUsuariosNew = attachedListaUsuariosNew;
            imagenPerfil.setListaUsuarios(listaUsuariosNew);
            imagenPerfil = em.merge(imagenPerfil);
            for (Usuarios listaUsuariosOldUsuarios : listaUsuariosOld) {
                if (!listaUsuariosNew.contains(listaUsuariosOldUsuarios)) {
                    listaUsuariosOldUsuarios.setImg(null);
                    listaUsuariosOldUsuarios = em.merge(listaUsuariosOldUsuarios);
                }
            }
            for (Usuarios listaUsuariosNewUsuarios : listaUsuariosNew) {
                if (!listaUsuariosOld.contains(listaUsuariosNewUsuarios)) {
                    ImagenPerfil oldImgOfListaUsuariosNewUsuarios = listaUsuariosNewUsuarios.getImg();
                    listaUsuariosNewUsuarios.setImg(imagenPerfil);
                    listaUsuariosNewUsuarios = em.merge(listaUsuariosNewUsuarios);
                    if (oldImgOfListaUsuariosNewUsuarios != null && !oldImgOfListaUsuariosNewUsuarios.equals(imagenPerfil)) {
                        oldImgOfListaUsuariosNewUsuarios.getListaUsuarios().remove(listaUsuariosNewUsuarios);
                        oldImgOfListaUsuariosNewUsuarios = em.merge(oldImgOfListaUsuariosNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = imagenPerfil.getId();
                if (findImagenPerfil(id) == null) {
                    throw new NonexistentEntityException("The imagenPerfil with id " + id + " no longer exists.");
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
            ImagenPerfil imagenPerfil;
            try {
                imagenPerfil = em.getReference(ImagenPerfil.class, id);
                imagenPerfil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagenPerfil with id " + id + " no longer exists.", enfe);
            }
            List<Usuarios> listaUsuarios = imagenPerfil.getListaUsuarios();
            for (Usuarios listaUsuariosUsuarios : listaUsuarios) {
                listaUsuariosUsuarios.setImg(null);
                listaUsuariosUsuarios = em.merge(listaUsuariosUsuarios);
            }
            em.remove(imagenPerfil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagenPerfil> findImagenPerfilEntities() {
        return findImagenPerfilEntities(true, -1, -1);
    }

    public List<ImagenPerfil> findImagenPerfilEntities(int maxResults, int firstResult) {
        return findImagenPerfilEntities(false, maxResults, firstResult);
    }

    private List<ImagenPerfil> findImagenPerfilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagenPerfil.class));
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

    public ImagenPerfil findImagenPerfil(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagenPerfil.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagenPerfilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagenPerfil> rt = cq.from(ImagenPerfil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ImagenPerfil findImagenByNombre(String nombreArchivo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM ImagenPerfil i WHERE i.nomArchivo = :nombreArchivo");
            query.setParameter("nombreArchivo", nombreArchivo);
            List<ImagenPerfil> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
        } finally {
            em.close();
        }
        return null;
    }

    public List<ImagenPerfil> getPredeterminedPfps() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT i FROM ImagenPerfil i WHERE i.origenArchivo = :origenArchivo");
            query.setParameter("origenArchivo", Enum.OrigenArchivo.PREDETERMINADA);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
