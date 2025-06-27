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
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author hp
 */
public class UsuarioRolesJpaController implements Serializable {

    public UsuarioRolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioRoles usuarioRoles) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuariouserrol = usuarioRoles.getUsuariouserrol();
            if (usuariouserrol != null) {
                usuariouserrol = em.getReference(usuariouserrol.getClass(), usuariouserrol.getId());
                usuarioRoles.setUsuariouserrol(usuariouserrol);
            }
            Roles roluserrol = usuarioRoles.getRoluserrol();
            if (roluserrol != null) {
                roluserrol = em.getReference(roluserrol.getClass(), roluserrol.getId());
                usuarioRoles.setRoluserrol(roluserrol);
            }
            Gremio gremiouserrol = usuarioRoles.getGremiouserrol();
            if (gremiouserrol != null) {
                gremiouserrol = em.getReference(gremiouserrol.getClass(), gremiouserrol.getId());
                usuarioRoles.setGremiouserrol(gremiouserrol);
            }
            em.persist(usuarioRoles);
            if (usuariouserrol != null) {
                usuariouserrol.getListaUsuariosRol().add(usuarioRoles);
                usuariouserrol = em.merge(usuariouserrol);
            }
            if (roluserrol != null) {
                roluserrol.getListaUsuariosRoles().add(usuarioRoles);
                roluserrol = em.merge(roluserrol);
            }
            if (gremiouserrol != null) {
                gremiouserrol.getListaUsuariosRoles().add(usuarioRoles);
                gremiouserrol = em.merge(gremiouserrol);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioRoles usuarioRoles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioRoles persistentUsuarioRoles = em.find(UsuarioRoles.class, usuarioRoles.getId());
            Usuarios usuariouserrolOld = persistentUsuarioRoles.getUsuariouserrol();
            Usuarios usuariouserrolNew = usuarioRoles.getUsuariouserrol();
            Roles roluserrolOld = persistentUsuarioRoles.getRoluserrol();
            Roles roluserrolNew = usuarioRoles.getRoluserrol();
            Gremio gremiouserrolOld = persistentUsuarioRoles.getGremiouserrol();
            Gremio gremiouserrolNew = usuarioRoles.getGremiouserrol();
            if (usuariouserrolNew != null) {
                usuariouserrolNew = em.getReference(usuariouserrolNew.getClass(), usuariouserrolNew.getId());
                usuarioRoles.setUsuariouserrol(usuariouserrolNew);
            }
            if (roluserrolNew != null) {
                roluserrolNew = em.getReference(roluserrolNew.getClass(), roluserrolNew.getId());
                usuarioRoles.setRoluserrol(roluserrolNew);
            }
            if (gremiouserrolNew != null) {
                gremiouserrolNew = em.getReference(gremiouserrolNew.getClass(), gremiouserrolNew.getId());
                usuarioRoles.setGremiouserrol(gremiouserrolNew);
            }
            usuarioRoles = em.merge(usuarioRoles);
            if (usuariouserrolOld != null && !usuariouserrolOld.equals(usuariouserrolNew)) {
                usuariouserrolOld.getListaUsuariosRol().remove(usuarioRoles);
                usuariouserrolOld = em.merge(usuariouserrolOld);
            }
            if (usuariouserrolNew != null && !usuariouserrolNew.equals(usuariouserrolOld)) {
                usuariouserrolNew.getListaUsuariosRol().add(usuarioRoles);
                usuariouserrolNew = em.merge(usuariouserrolNew);
            }
            if (roluserrolOld != null && !roluserrolOld.equals(roluserrolNew)) {
                roluserrolOld.getListaUsuariosRoles().remove(usuarioRoles);
                roluserrolOld = em.merge(roluserrolOld);
            }
            if (roluserrolNew != null && !roluserrolNew.equals(roluserrolOld)) {
                roluserrolNew.getListaUsuariosRoles().add(usuarioRoles);
                roluserrolNew = em.merge(roluserrolNew);
            }
            if (gremiouserrolOld != null && !gremiouserrolOld.equals(gremiouserrolNew)) {
                gremiouserrolOld.getListaUsuariosRoles().remove(usuarioRoles);
                gremiouserrolOld = em.merge(gremiouserrolOld);
            }
            if (gremiouserrolNew != null && !gremiouserrolNew.equals(gremiouserrolOld)) {
                gremiouserrolNew.getListaUsuariosRoles().add(usuarioRoles);
                gremiouserrolNew = em.merge(gremiouserrolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuarioRoles.getId();
                if (findUsuarioRoles(id) == null) {
                    throw new NonexistentEntityException("The usuarioRoles with id " + id + " no longer exists.");
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
            UsuarioRoles usuarioRoles;
            try {
                usuarioRoles = em.getReference(UsuarioRoles.class, id);
                usuarioRoles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioRoles with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuariouserrol = usuarioRoles.getUsuariouserrol();
            if (usuariouserrol != null) {
                usuariouserrol.getListaUsuariosRol().remove(usuarioRoles);
                usuariouserrol = em.merge(usuariouserrol);
            }
            Roles roluserrol = usuarioRoles.getRoluserrol();
            if (roluserrol != null) {
                roluserrol.getListaUsuariosRoles().remove(usuarioRoles);
                roluserrol = em.merge(roluserrol);
            }
            Gremio gremiouserrol = usuarioRoles.getGremiouserrol();
            if (gremiouserrol != null) {
                gremiouserrol.getListaUsuariosRoles().remove(usuarioRoles);
                gremiouserrol = em.merge(gremiouserrol);
            }
            em.remove(usuarioRoles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioRoles> findUsuarioRolesEntities() {
        return findUsuarioRolesEntities(true, -1, -1);
    }

    public List<UsuarioRoles> findUsuarioRolesEntities(int maxResults, int firstResult) {
        return findUsuarioRolesEntities(false, maxResults, firstResult);
    }

    private List<UsuarioRoles> findUsuarioRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioRoles.class));
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

    public UsuarioRoles findUsuarioRoles(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioRoles.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioRoles> rt = cq.from(UsuarioRoles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
