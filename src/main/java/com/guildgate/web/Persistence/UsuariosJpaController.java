package com.guildgate.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.Participaciones;
import java.util.HashSet;
import java.util.Set;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class UsuariosJpaController extends AbstractJpaController implements Serializable {

    public void create(Usuarios usuarios) {
        if (usuarios.getListaParticipaciones() == null) {
            usuarios.setListaParticipaciones(new HashSet<Participaciones>());
        }
        if (usuarios.getListaUsuariosRol() == null) {
            usuarios.setListaUsuariosRol(new HashSet<UsuarioRoles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gremio gremiousuario = usuarios.getGremiousuario();
            if (gremiousuario != null) {
                gremiousuario = em.getReference(gremiousuario.getClass(), gremiousuario.getId());
                usuarios.setGremiousuario(gremiousuario);
            }
            ImagenPerfil img = usuarios.getImg();
            if (img != null) {
                img = em.getReference(img.getClass(), img.getId());
                usuarios.setImg(img);
            }
            ImagenBanner imgB = usuarios.getImgB();
            if (imgB != null) {
                imgB = em.getReference(imgB.getClass(), imgB.getId());
                usuarios.setImgB(imgB);
            }
            Set<Participaciones> attachedListaParticipaciones = new HashSet<Participaciones>();
            for (Participaciones listaParticipacionesParticipacionesToAttach : usuarios.getListaParticipaciones()) {
                listaParticipacionesParticipacionesToAttach = em.getReference(listaParticipacionesParticipacionesToAttach.getClass(), listaParticipacionesParticipacionesToAttach.getId());
                attachedListaParticipaciones.add(listaParticipacionesParticipacionesToAttach);
            }
            usuarios.setListaParticipaciones(attachedListaParticipaciones);
            Set<UsuarioRoles> attachedListaUsuariosRol = new HashSet<UsuarioRoles>();
            for (UsuarioRoles listaUsuariosRolUsuarioRolesToAttach : usuarios.getListaUsuariosRol()) {
                listaUsuariosRolUsuarioRolesToAttach = em.getReference(listaUsuariosRolUsuarioRolesToAttach.getClass(), listaUsuariosRolUsuarioRolesToAttach.getId());
                attachedListaUsuariosRol.add(listaUsuariosRolUsuarioRolesToAttach);
            }
            usuarios.setListaUsuariosRol(attachedListaUsuariosRol);
            em.persist(usuarios);
            if (gremiousuario != null) {
                gremiousuario.getListaUsuarios().add(usuarios);
                gremiousuario = em.merge(gremiousuario);
            }
            if (img != null) {
                img.getListaUsuarios().add(usuarios);
                img = em.merge(img);
            }
            if (imgB != null) {
                imgB.getListaJugadores().add(usuarios);
                imgB = em.merge(imgB);
            }
            for (Participaciones listaParticipacionesParticipaciones : usuarios.getListaParticipaciones()) {
                Usuarios oldUserpartiOfListaParticipacionesParticipaciones = listaParticipacionesParticipaciones.getUserparti();
                listaParticipacionesParticipaciones.setUserparti(usuarios);
                listaParticipacionesParticipaciones = em.merge(listaParticipacionesParticipaciones);
                if (oldUserpartiOfListaParticipacionesParticipaciones != null) {
                    oldUserpartiOfListaParticipacionesParticipaciones.getListaParticipaciones().remove(listaParticipacionesParticipaciones);
                    oldUserpartiOfListaParticipacionesParticipaciones = em.merge(oldUserpartiOfListaParticipacionesParticipaciones);
                }
            }
            for (UsuarioRoles listaUsuariosRolUsuarioRoles : usuarios.getListaUsuariosRol()) {
                Usuarios oldUsuariouserrolOfListaUsuariosRolUsuarioRoles = listaUsuariosRolUsuarioRoles.getUsuariouserrol();
                listaUsuariosRolUsuarioRoles.setUsuariouserrol(usuarios);
                listaUsuariosRolUsuarioRoles = em.merge(listaUsuariosRolUsuarioRoles);
                if (oldUsuariouserrolOfListaUsuariosRolUsuarioRoles != null) {
                    oldUsuariouserrolOfListaUsuariosRolUsuarioRoles.getListaUsuariosRol().remove(listaUsuariosRolUsuarioRoles);
                    oldUsuariouserrolOfListaUsuariosRolUsuarioRoles = em.merge(oldUsuariouserrolOfListaUsuariosRolUsuarioRoles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            Gremio gremiousuarioOld = persistentUsuarios.getGremiousuario();
            Gremio gremiousuarioNew = usuarios.getGremiousuario();
            ImagenPerfil imgOld = persistentUsuarios.getImg();
            ImagenPerfil imgNew = usuarios.getImg();
            ImagenBanner imgBOld = persistentUsuarios.getImgB();
            ImagenBanner imgBNew = usuarios.getImgB();
            Set<Participaciones> listaParticipacionesOld = persistentUsuarios.getListaParticipaciones();
            Set<Participaciones> listaParticipacionesNew = usuarios.getListaParticipaciones();
            Set<UsuarioRoles> listaUsuariosRolOld = persistentUsuarios.getListaUsuariosRol();
            Set<UsuarioRoles> listaUsuariosRolNew = usuarios.getListaUsuariosRol();
            List<String> illegalOrphanMessages = null;
            for (Participaciones listaParticipacionesOldParticipaciones : listaParticipacionesOld) {
                if (!listaParticipacionesNew.contains(listaParticipacionesOldParticipaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participaciones " + listaParticipacionesOldParticipaciones + " since its userparti field is not nullable.");
                }
            }
            for (UsuarioRoles listaUsuariosRolOldUsuarioRoles : listaUsuariosRolOld) {
                if (!listaUsuariosRolNew.contains(listaUsuariosRolOldUsuarioRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioRoles " + listaUsuariosRolOldUsuarioRoles + " since its usuariouserrol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (gremiousuarioNew != null) {
                gremiousuarioNew = em.getReference(gremiousuarioNew.getClass(), gremiousuarioNew.getId());
                usuarios.setGremiousuario(gremiousuarioNew);
            }
            if (imgNew != null) {
                imgNew = em.getReference(imgNew.getClass(), imgNew.getId());
                usuarios.setImg(imgNew);
            }
            if (imgBNew != null) {
                imgBNew = em.getReference(imgBNew.getClass(), imgBNew.getId());
                usuarios.setImgB(imgBNew);
            }
            Set<Participaciones> attachedListaParticipacionesNew = new HashSet<Participaciones>();
            for (Participaciones listaParticipacionesNewParticipacionesToAttach : listaParticipacionesNew) {
                listaParticipacionesNewParticipacionesToAttach = em.getReference(listaParticipacionesNewParticipacionesToAttach.getClass(), listaParticipacionesNewParticipacionesToAttach.getId());
                attachedListaParticipacionesNew.add(listaParticipacionesNewParticipacionesToAttach);
            }
            listaParticipacionesNew = attachedListaParticipacionesNew;
            usuarios.setListaParticipaciones(listaParticipacionesNew);
            Set<UsuarioRoles> attachedListaUsuariosRolNew = new HashSet<UsuarioRoles>();
            for (UsuarioRoles listaUsuariosRolNewUsuarioRolesToAttach : listaUsuariosRolNew) {
                listaUsuariosRolNewUsuarioRolesToAttach = em.getReference(listaUsuariosRolNewUsuarioRolesToAttach.getClass(), listaUsuariosRolNewUsuarioRolesToAttach.getId());
                attachedListaUsuariosRolNew.add(listaUsuariosRolNewUsuarioRolesToAttach);
            }
            listaUsuariosRolNew = attachedListaUsuariosRolNew;
            usuarios.setListaUsuariosRol(listaUsuariosRolNew);
            usuarios = em.merge(usuarios);
            if (gremiousuarioOld != null && !gremiousuarioOld.equals(gremiousuarioNew)) {
                gremiousuarioOld.getListaUsuarios().remove(usuarios);
                gremiousuarioOld = em.merge(gremiousuarioOld);
            }
            if (gremiousuarioNew != null && !gremiousuarioNew.equals(gremiousuarioOld)) {
                gremiousuarioNew.getListaUsuarios().add(usuarios);
                gremiousuarioNew = em.merge(gremiousuarioNew);
            }
            if (imgOld != null && !imgOld.equals(imgNew)) {
                imgOld.getListaUsuarios().remove(usuarios);
                imgOld = em.merge(imgOld);
            }
            if (imgNew != null && !imgNew.equals(imgOld)) {
                imgNew.getListaUsuarios().add(usuarios);
                imgNew = em.merge(imgNew);
            }
            if (imgBOld != null && !imgBOld.equals(imgBNew)) {
                imgBOld.getListaJugadores().remove(usuarios);
                imgBOld = em.merge(imgBOld);
            }
            if (imgBNew != null && !imgBNew.equals(imgBOld)) {
                imgBNew.getListaJugadores().add(usuarios);
                imgBNew = em.merge(imgBNew);
            }
            for (Participaciones listaParticipacionesNewParticipaciones : listaParticipacionesNew) {
                if (!listaParticipacionesOld.contains(listaParticipacionesNewParticipaciones)) {
                    Usuarios oldUserpartiOfListaParticipacionesNewParticipaciones = listaParticipacionesNewParticipaciones.getUserparti();
                    listaParticipacionesNewParticipaciones.setUserparti(usuarios);
                    listaParticipacionesNewParticipaciones = em.merge(listaParticipacionesNewParticipaciones);
                    if (oldUserpartiOfListaParticipacionesNewParticipaciones != null && !oldUserpartiOfListaParticipacionesNewParticipaciones.equals(usuarios)) {
                        oldUserpartiOfListaParticipacionesNewParticipaciones.getListaParticipaciones().remove(listaParticipacionesNewParticipaciones);
                        oldUserpartiOfListaParticipacionesNewParticipaciones = em.merge(oldUserpartiOfListaParticipacionesNewParticipaciones);
                    }
                }
            }
            for (UsuarioRoles listaUsuariosRolNewUsuarioRoles : listaUsuariosRolNew) {
                if (!listaUsuariosRolOld.contains(listaUsuariosRolNewUsuarioRoles)) {
                    Usuarios oldUsuariouserrolOfListaUsuariosRolNewUsuarioRoles = listaUsuariosRolNewUsuarioRoles.getUsuariouserrol();
                    listaUsuariosRolNewUsuarioRoles.setUsuariouserrol(usuarios);
                    listaUsuariosRolNewUsuarioRoles = em.merge(listaUsuariosRolNewUsuarioRoles);
                    if (oldUsuariouserrolOfListaUsuariosRolNewUsuarioRoles != null && !oldUsuariouserrolOfListaUsuariosRolNewUsuarioRoles.equals(usuarios)) {
                        oldUsuariouserrolOfListaUsuariosRolNewUsuarioRoles.getListaUsuariosRol().remove(listaUsuariosRolNewUsuarioRoles);
                        oldUsuariouserrolOfListaUsuariosRolNewUsuarioRoles = em.merge(oldUsuariouserrolOfListaUsuariosRolNewUsuarioRoles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editAvatarUser(int id, ImagenPerfil img) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios perUser = em.find(Usuarios.class, id);
            if (perUser == null) {
                throw new NonexistentEntityException("El usuario con id " + id + " ya no existe.");
            }

            perUser.setImg(img);

            em.merge(perUser);
            em.getTransaction().commit();
        } catch (NonexistentEntityException ex) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("El usuario con id " + id + " ya no existe.");
                }
            }
            throw new Exception("Error al editar el avatar del usuario", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editBannerUser(int id, ImagenBanner img) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios perUser = em.find(Usuarios.class, id);
            if (perUser == null) {
                throw new NonexistentEntityException("El usuario con id " + id + " ya no existe.");
            }

            perUser.setImgB(img);

            em.merge(perUser);
            em.getTransaction().commit();
        } catch (NonexistentEntityException ex) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("El usuario con id " + id + " ya no existe.");
                }
            }
            throw new Exception("Error al editar el banner del usuario", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editBasicInfo(int id, String nombre, String correo, String contrasena, String bio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios perUser = em.find(Usuarios.class, id);
            if (perUser == null) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
            }

            perUser.setNombre(nombre);
            perUser.setCorreo(correo);
            perUser.setContrasena(contrasena);
            perUser.setBio(bio);

            em.merge(perUser);
            em.getTransaction().commit();
        } catch (NonexistentEntityException ex) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Participaciones> listaParticipacionesOrphanCheck = usuarios.getListaParticipaciones();
            for (Participaciones listaParticipacionesOrphanCheckParticipaciones : listaParticipacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Participaciones " + listaParticipacionesOrphanCheckParticipaciones + " in its listaParticipaciones field has a non-nullable userparti field.");
            }
            Set<UsuarioRoles> listaUsuariosRolOrphanCheck = usuarios.getListaUsuariosRol();
            for (UsuarioRoles listaUsuariosRolOrphanCheckUsuarioRoles : listaUsuariosRolOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the UsuarioRoles " + listaUsuariosRolOrphanCheckUsuarioRoles + " in its listaUsuariosRol field has a non-nullable usuariouserrol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Gremio gremiousuario = usuarios.getGremiousuario();
            if (gremiousuario != null) {
                gremiousuario.getListaUsuarios().remove(usuarios);
                gremiousuario = em.merge(gremiousuario);
            }
            ImagenPerfil img = usuarios.getImg();
            if (img != null) {
                img.getListaUsuarios().remove(usuarios);
                img = em.merge(img);
            }
            ImagenBanner imgB = usuarios.getImgB();
            if (imgB != null) {
                imgB.getListaJugadores().remove(usuarios);
                imgB = em.merge(imgB);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public long contarUsuariosPorGremio(int gremioId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM Usuarios u WHERE u.gremiousuario.id = :gremioId";
            Query query = em.createQuery(jpql);
            query.setParameter("gremioId", gremioId);

            Long count = (Long) query.getSingleResult();
            return count;
        } finally {
            em.close();
        }
    }
}
