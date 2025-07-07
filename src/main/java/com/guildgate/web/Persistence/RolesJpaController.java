package com.guildgate.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.RolPermiso;
import com.guildgate.web.Modelo.Roles;
import java.util.HashSet;
import java.util.Set;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class RolesJpaController extends AbstractJpaController implements Serializable {

    public void create(Roles roles) {
        if (roles.getRolPermisos() == null) {
            roles.setRolPermisos(new HashSet<RolPermiso>());
        }
        if (roles.getListaUsuariosRoles() == null) {
            roles.setListaUsuariosRoles(new HashSet<UsuarioRoles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<RolPermiso> attachedRolPermisos = new HashSet<RolPermiso>();
            for (RolPermiso rolPermisosRolPermisoToAttach : roles.getRolPermisos()) {
                rolPermisosRolPermisoToAttach = em.getReference(rolPermisosRolPermisoToAttach.getClass(), rolPermisosRolPermisoToAttach.getId());
                attachedRolPermisos.add(rolPermisosRolPermisoToAttach);
            }
            roles.setRolPermisos(attachedRolPermisos);
            Set<UsuarioRoles> attachedListaUsuariosRoles = new HashSet<UsuarioRoles>();
            for (UsuarioRoles listaUsuariosRolesUsuarioRolesToAttach : roles.getListaUsuariosRoles()) {
                listaUsuariosRolesUsuarioRolesToAttach = em.getReference(listaUsuariosRolesUsuarioRolesToAttach.getClass(), listaUsuariosRolesUsuarioRolesToAttach.getId());
                attachedListaUsuariosRoles.add(listaUsuariosRolesUsuarioRolesToAttach);
            }
            roles.setListaUsuariosRoles(attachedListaUsuariosRoles);
            em.persist(roles);
            for (RolPermiso rolPermisosRolPermiso : roles.getRolPermisos()) {
                Roles oldRolOfRolPermisosRolPermiso = rolPermisosRolPermiso.getRol();
                rolPermisosRolPermiso.setRol(roles);
                rolPermisosRolPermiso = em.merge(rolPermisosRolPermiso);
                if (oldRolOfRolPermisosRolPermiso != null) {
                    oldRolOfRolPermisosRolPermiso.getRolPermisos().remove(rolPermisosRolPermiso);
                    oldRolOfRolPermisosRolPermiso = em.merge(oldRolOfRolPermisosRolPermiso);
                }
            }
            for (UsuarioRoles listaUsuariosRolesUsuarioRoles : roles.getListaUsuariosRoles()) {
                Roles oldRoluserrolOfListaUsuariosRolesUsuarioRoles = listaUsuariosRolesUsuarioRoles.getRoluserrol();
                listaUsuariosRolesUsuarioRoles.setRoluserrol(roles);
                listaUsuariosRolesUsuarioRoles = em.merge(listaUsuariosRolesUsuarioRoles);
                if (oldRoluserrolOfListaUsuariosRolesUsuarioRoles != null) {
                    oldRoluserrolOfListaUsuariosRolesUsuarioRoles.getListaUsuariosRoles().remove(listaUsuariosRolesUsuarioRoles);
                    oldRoluserrolOfListaUsuariosRolesUsuarioRoles = em.merge(oldRoluserrolOfListaUsuariosRolesUsuarioRoles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getId());
            Set<RolPermiso> rolPermisosOld = persistentRoles.getRolPermisos();
            Set<RolPermiso> rolPermisosNew = roles.getRolPermisos();
            Set<UsuarioRoles> listaUsuariosRolesOld = persistentRoles.getListaUsuariosRoles();
            Set<UsuarioRoles> listaUsuariosRolesNew = roles.getListaUsuariosRoles();
            List<String> illegalOrphanMessages = null;
            for (RolPermiso rolPermisosOldRolPermiso : rolPermisosOld) {
                if (!rolPermisosNew.contains(rolPermisosOldRolPermiso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RolPermiso " + rolPermisosOldRolPermiso + " since its rol field is not nullable.");
                }
            }
            for (UsuarioRoles listaUsuariosRolesOldUsuarioRoles : listaUsuariosRolesOld) {
                if (!listaUsuariosRolesNew.contains(listaUsuariosRolesOldUsuarioRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioRoles " + listaUsuariosRolesOldUsuarioRoles + " since its roluserrol field is not nullable.");
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
            roles.setRolPermisos(rolPermisosNew);
            Set<UsuarioRoles> attachedListaUsuariosRolesNew = new HashSet<UsuarioRoles>();
            for (UsuarioRoles listaUsuariosRolesNewUsuarioRolesToAttach : listaUsuariosRolesNew) {
                listaUsuariosRolesNewUsuarioRolesToAttach = em.getReference(listaUsuariosRolesNewUsuarioRolesToAttach.getClass(), listaUsuariosRolesNewUsuarioRolesToAttach.getId());
                attachedListaUsuariosRolesNew.add(listaUsuariosRolesNewUsuarioRolesToAttach);
            }
            listaUsuariosRolesNew = attachedListaUsuariosRolesNew;
            roles.setListaUsuariosRoles(listaUsuariosRolesNew);
            roles = em.merge(roles);
            for (RolPermiso rolPermisosNewRolPermiso : rolPermisosNew) {
                if (!rolPermisosOld.contains(rolPermisosNewRolPermiso)) {
                    Roles oldRolOfRolPermisosNewRolPermiso = rolPermisosNewRolPermiso.getRol();
                    rolPermisosNewRolPermiso.setRol(roles);
                    rolPermisosNewRolPermiso = em.merge(rolPermisosNewRolPermiso);
                    if (oldRolOfRolPermisosNewRolPermiso != null && !oldRolOfRolPermisosNewRolPermiso.equals(roles)) {
                        oldRolOfRolPermisosNewRolPermiso.getRolPermisos().remove(rolPermisosNewRolPermiso);
                        oldRolOfRolPermisosNewRolPermiso = em.merge(oldRolOfRolPermisosNewRolPermiso);
                    }
                }
            }
            for (UsuarioRoles listaUsuariosRolesNewUsuarioRoles : listaUsuariosRolesNew) {
                if (!listaUsuariosRolesOld.contains(listaUsuariosRolesNewUsuarioRoles)) {
                    Roles oldRoluserrolOfListaUsuariosRolesNewUsuarioRoles = listaUsuariosRolesNewUsuarioRoles.getRoluserrol();
                    listaUsuariosRolesNewUsuarioRoles.setRoluserrol(roles);
                    listaUsuariosRolesNewUsuarioRoles = em.merge(listaUsuariosRolesNewUsuarioRoles);
                    if (oldRoluserrolOfListaUsuariosRolesNewUsuarioRoles != null && !oldRoluserrolOfListaUsuariosRolesNewUsuarioRoles.equals(roles)) {
                        oldRoluserrolOfListaUsuariosRolesNewUsuarioRoles.getListaUsuariosRoles().remove(listaUsuariosRolesNewUsuarioRoles);
                        oldRoluserrolOfListaUsuariosRolesNewUsuarioRoles = em.merge(oldRoluserrolOfListaUsuariosRolesNewUsuarioRoles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roles.getId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<RolPermiso> rolPermisosOrphanCheck = roles.getRolPermisos();
            for (RolPermiso rolPermisosOrphanCheckRolPermiso : rolPermisosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the RolPermiso " + rolPermisosOrphanCheckRolPermiso + " in its rolPermisos field has a non-nullable rol field.");
            }
            Set<UsuarioRoles> listaUsuariosRolesOrphanCheck = roles.getListaUsuariosRoles();
            for (UsuarioRoles listaUsuariosRolesOrphanCheckUsuarioRoles : listaUsuariosRolesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the UsuarioRoles " + listaUsuariosRolesOrphanCheckUsuarioRoles + " in its listaUsuariosRoles field has a non-nullable roluserrol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
