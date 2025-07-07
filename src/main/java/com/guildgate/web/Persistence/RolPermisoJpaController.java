package com.guildgate.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Modelo.Permiso;
import com.guildgate.web.Modelo.RolPermiso;
import com.guildgate.web.Modelo.RolPermisoId;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Persistence.exceptions.PreexistingEntityException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class RolPermisoJpaController extends AbstractJpaController implements Serializable {

    public void create(RolPermiso rolPermiso) throws PreexistingEntityException, Exception {
        if (rolPermiso.getId() == null) {
            rolPermiso.setId(new RolPermisoId());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles rol = rolPermiso.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getId());
                rolPermiso.setRol(rol);
            }
            Permiso permiso = rolPermiso.getPermiso();
            if (permiso != null) {
                permiso = em.getReference(permiso.getClass(), permiso.getId());
                rolPermiso.setPermiso(permiso);
            }
            em.persist(rolPermiso);
            if (rol != null) {
                rol.getRolPermisos().add(rolPermiso);
                rol = em.merge(rol);
            }
            if (permiso != null) {
                permiso.getRolPermisos().add(rolPermiso);
                permiso = em.merge(permiso);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRolPermiso(rolPermiso.getId()) != null) {
                throw new PreexistingEntityException("RolPermiso " + rolPermiso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RolPermiso rolPermiso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RolPermiso persistentRolPermiso = em.find(RolPermiso.class, rolPermiso.getId());
            Roles rolOld = persistentRolPermiso.getRol();
            Roles rolNew = rolPermiso.getRol();
            Permiso permisoOld = persistentRolPermiso.getPermiso();
            Permiso permisoNew = rolPermiso.getPermiso();
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getId());
                rolPermiso.setRol(rolNew);
            }
            if (permisoNew != null) {
                permisoNew = em.getReference(permisoNew.getClass(), permisoNew.getId());
                rolPermiso.setPermiso(permisoNew);
            }
            rolPermiso = em.merge(rolPermiso);
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getRolPermisos().remove(rolPermiso);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getRolPermisos().add(rolPermiso);
                rolNew = em.merge(rolNew);
            }
            if (permisoOld != null && !permisoOld.equals(permisoNew)) {
                permisoOld.getRolPermisos().remove(rolPermiso);
                permisoOld = em.merge(permisoOld);
            }
            if (permisoNew != null && !permisoNew.equals(permisoOld)) {
                permisoNew.getRolPermisos().add(rolPermiso);
                permisoNew = em.merge(permisoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RolPermisoId id = rolPermiso.getId();
                if (findRolPermiso(id) == null) {
                    throw new NonexistentEntityException("The rolPermiso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RolPermisoId id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RolPermiso rolPermiso;
            try {
                rolPermiso = em.getReference(RolPermiso.class, id);
                rolPermiso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolPermiso with id " + id + " no longer exists.", enfe);
            }
            Roles rol = rolPermiso.getRol();
            if (rol != null) {
                rol.getRolPermisos().remove(rolPermiso);
                rol = em.merge(rol);
            }
            Permiso permiso = rolPermiso.getPermiso();
            if (permiso != null) {
                permiso.getRolPermisos().remove(rolPermiso);
                permiso = em.merge(permiso);
            }
            em.remove(rolPermiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RolPermiso> findRolPermisoEntities() {
        return findRolPermisoEntities(true, -1, -1);
    }

    public List<RolPermiso> findRolPermisoEntities(int maxResults, int firstResult) {
        return findRolPermisoEntities(false, maxResults, firstResult);
    }

    private List<RolPermiso> findRolPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RolPermiso.class));
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

    public RolPermiso findRolPermiso(RolPermisoId id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RolPermiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RolPermiso> rt = cq.from(RolPermiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
