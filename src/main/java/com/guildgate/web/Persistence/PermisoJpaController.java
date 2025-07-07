package com.guildgate.web.Persistence;

import com.guildgate.web.Modelo.Permiso;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.RolPermiso;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class PermisoJpaController extends AbstractJpaController implements Serializable {

    public void create(Permiso permiso) {
        if (permiso.getRolPermisos() == null) {
            permiso.setRolPermisos(new HashSet<RolPermiso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<RolPermiso> attachedRolPermisos = new HashSet<RolPermiso>();
            for (RolPermiso rolPermisosRolPermisoToAttach : permiso.getRolPermisos()) {
                rolPermisosRolPermisoToAttach = em.getReference(rolPermisosRolPermisoToAttach.getClass(), rolPermisosRolPermisoToAttach.getId());
                attachedRolPermisos.add(rolPermisosRolPermisoToAttach);
            }
            permiso.setRolPermisos(attachedRolPermisos);
            em.persist(permiso);
            for (RolPermiso rolPermisosRolPermiso : permiso.getRolPermisos()) {
                Permiso oldPermisoOfRolPermisosRolPermiso = rolPermisosRolPermiso.getPermiso();
                rolPermisosRolPermiso.setPermiso(permiso);
                rolPermisosRolPermiso = em.merge(rolPermisosRolPermiso);
                if (oldPermisoOfRolPermisosRolPermiso != null) {
                    oldPermisoOfRolPermisosRolPermiso.getRolPermisos().remove(rolPermisosRolPermiso);
                    oldPermisoOfRolPermisosRolPermiso = em.merge(oldPermisoOfRolPermisosRolPermiso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permiso permiso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso persistentPermiso = em.find(Permiso.class, permiso.getId());
            Set<RolPermiso> rolPermisosOld = persistentPermiso.getRolPermisos();
            Set<RolPermiso> rolPermisosNew = permiso.getRolPermisos();
            List<String> illegalOrphanMessages = null;
            for (RolPermiso rolPermisosOldRolPermiso : rolPermisosOld) {
                if (!rolPermisosNew.contains(rolPermisosOldRolPermiso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RolPermiso " + rolPermisosOldRolPermiso + " since its permiso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<RolPermiso> attachedRolPermisosNew = new HashSet<RolPermiso>();
            for (RolPermiso rolPermisosNewRolPermisoToAttach : rolPermisosNew) {
                rolPermisosNewRolPermisoToAttach = em.getReference(rolPermisosNewRolPermisoToAttach.getClass(), rolPermisosNewRolPermisoToAttach.getId());
                attachedRolPermisosNew.add(rolPermisosNewRolPermisoToAttach);
            }
            rolPermisosNew = attachedRolPermisosNew;
            permiso.setRolPermisos(rolPermisosNew);
            permiso = em.merge(permiso);
            for (RolPermiso rolPermisosNewRolPermiso : rolPermisosNew) {
                if (!rolPermisosOld.contains(rolPermisosNewRolPermiso)) {
                    Permiso oldPermisoOfRolPermisosNewRolPermiso = rolPermisosNewRolPermiso.getPermiso();
                    rolPermisosNewRolPermiso.setPermiso(permiso);
                    rolPermisosNewRolPermiso = em.merge(rolPermisosNewRolPermiso);
                    if (oldPermisoOfRolPermisosNewRolPermiso != null && !oldPermisoOfRolPermisosNewRolPermiso.equals(permiso)) {
                        oldPermisoOfRolPermisosNewRolPermiso.getRolPermisos().remove(rolPermisosNewRolPermiso);
                        oldPermisoOfRolPermisosNewRolPermiso = em.merge(oldPermisoOfRolPermisosNewRolPermiso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permiso.getId();
                if (findPermiso(id) == null) {
                    throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.");
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
            Permiso permiso;
            try {
                permiso = em.getReference(Permiso.class, id);
                permiso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<RolPermiso> rolPermisosOrphanCheck = permiso.getRolPermisos();
            for (RolPermiso rolPermisosOrphanCheckRolPermiso : rolPermisosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Permiso (" + permiso + ") cannot be destroyed since the RolPermiso " + rolPermisosOrphanCheckRolPermiso + " in its rolPermisos field has a non-nullable permiso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(permiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permiso> findPermisoEntities() {
        return findPermisoEntities(true, -1, -1);
    }

    public List<Permiso> findPermisoEntities(int maxResults, int firstResult) {
        return findPermisoEntities(false, maxResults, firstResult);
    }

    private List<Permiso> findPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permiso.class));
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

    public Permiso findPermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permiso> rt = cq.from(Permiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
