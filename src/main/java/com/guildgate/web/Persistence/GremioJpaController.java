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
import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Raid;
import java.util.HashSet;
import java.util.Set;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class GremioJpaController implements Serializable {

    public GremioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gremio gremio) {
        if (gremio.getListaRaid() == null) {
            gremio.setListaRaid(new HashSet<Raid>());
        }
        if (gremio.getListaUsuarios() == null) {
            gremio.setListaUsuarios(new HashSet<Usuarios>());
        }
        if (gremio.getListaUsuariosRoles() == null) {
            gremio.setListaUsuariosRoles(new HashSet<UsuarioRoles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region gremioregion = gremio.getGremioregion();
            if (gremioregion != null) {
                gremioregion = em.getReference(gremioregion.getClass(), gremioregion.getId());
                gremio.setGremioregion(gremioregion);
            }
            Mundos gremiomundo = gremio.getGremiomundo();
            if (gremiomundo != null) {
                gremiomundo = em.getReference(gremiomundo.getClass(), gremiomundo.getId());
                gremio.setGremiomundo(gremiomundo);
            }
            AvatarGremio img = gremio.getImg();
            if (img != null) {
                img = em.getReference(img.getClass(), img.getId());
                gremio.setImg(img);
            }
            FondoGremio imgF = gremio.getImgF();
            if (imgF != null) {
                imgF = em.getReference(imgF.getClass(), imgF.getId());
                gremio.setImgF(imgF);
            }
            Set<Raid> attachedListaRaid = new HashSet<Raid>();
            for (Raid listaRaidRaidToAttach : gremio.getListaRaid()) {
                listaRaidRaidToAttach = em.getReference(listaRaidRaidToAttach.getClass(), listaRaidRaidToAttach.getId());
                attachedListaRaid.add(listaRaidRaidToAttach);
            }
            gremio.setListaRaid(attachedListaRaid);
            Set<Usuarios> attachedListaUsuarios = new HashSet<Usuarios>();
            for (Usuarios listaUsuariosUsuariosToAttach : gremio.getListaUsuarios()) {
                listaUsuariosUsuariosToAttach = em.getReference(listaUsuariosUsuariosToAttach.getClass(), listaUsuariosUsuariosToAttach.getId());
                attachedListaUsuarios.add(listaUsuariosUsuariosToAttach);
            }
            gremio.setListaUsuarios(attachedListaUsuarios);
            Set<UsuarioRoles> attachedListaUsuariosRoles = new HashSet<UsuarioRoles>();
            for (UsuarioRoles listaUsuariosRolesUsuarioRolesToAttach : gremio.getListaUsuariosRoles()) {
                listaUsuariosRolesUsuarioRolesToAttach = em.getReference(listaUsuariosRolesUsuarioRolesToAttach.getClass(), listaUsuariosRolesUsuarioRolesToAttach.getId());
                attachedListaUsuariosRoles.add(listaUsuariosRolesUsuarioRolesToAttach);
            }
            gremio.setListaUsuariosRoles(attachedListaUsuariosRoles);
            em.persist(gremio);
            if (gremioregion != null) {
                gremioregion.getListaGremios().add(gremio);
                gremioregion = em.merge(gremioregion);
            }
            if (gremiomundo != null) {
                gremiomundo.getListaGremios().add(gremio);
                gremiomundo = em.merge(gremiomundo);
            }
            if (img != null) {
                img.getListaGremios().add(gremio);
                img = em.merge(img);
            }
            if (imgF != null) {
                imgF.getListaGremios().add(gremio);
                imgF = em.merge(imgF);
            }
            for (Raid listaRaidRaid : gremio.getListaRaid()) {
                Gremio oldRaidgremioOfListaRaidRaid = listaRaidRaid.getRaidgremio();
                listaRaidRaid.setRaidgremio(gremio);
                listaRaidRaid = em.merge(listaRaidRaid);
                if (oldRaidgremioOfListaRaidRaid != null) {
                    oldRaidgremioOfListaRaidRaid.getListaRaid().remove(listaRaidRaid);
                    oldRaidgremioOfListaRaidRaid = em.merge(oldRaidgremioOfListaRaidRaid);
                }
            }
            for (Usuarios listaUsuariosUsuarios : gremio.getListaUsuarios()) {
                Gremio oldGremiousuarioOfListaUsuariosUsuarios = listaUsuariosUsuarios.getGremiousuario();
                listaUsuariosUsuarios.setGremiousuario(gremio);
                listaUsuariosUsuarios = em.merge(listaUsuariosUsuarios);
                if (oldGremiousuarioOfListaUsuariosUsuarios != null) {
                    oldGremiousuarioOfListaUsuariosUsuarios.getListaUsuarios().remove(listaUsuariosUsuarios);
                    oldGremiousuarioOfListaUsuariosUsuarios = em.merge(oldGremiousuarioOfListaUsuariosUsuarios);
                }
            }
            for (UsuarioRoles listaUsuariosRolesUsuarioRoles : gremio.getListaUsuariosRoles()) {
                Gremio oldGremiouserrolOfListaUsuariosRolesUsuarioRoles = listaUsuariosRolesUsuarioRoles.getGremiouserrol();
                listaUsuariosRolesUsuarioRoles.setGremiouserrol(gremio);
                listaUsuariosRolesUsuarioRoles = em.merge(listaUsuariosRolesUsuarioRoles);
                if (oldGremiouserrolOfListaUsuariosRolesUsuarioRoles != null) {
                    oldGremiouserrolOfListaUsuariosRolesUsuarioRoles.getListaUsuariosRoles().remove(listaUsuariosRolesUsuarioRoles);
                    oldGremiouserrolOfListaUsuariosRolesUsuarioRoles = em.merge(oldGremiouserrolOfListaUsuariosRolesUsuarioRoles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gremio gremio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gremio persistentGremio = em.find(Gremio.class, gremio.getId());
            Region gremioregionOld = persistentGremio.getGremioregion();
            Region gremioregionNew = gremio.getGremioregion();
            Mundos gremiomundoOld = persistentGremio.getGremiomundo();
            Mundos gremiomundoNew = gremio.getGremiomundo();
            AvatarGremio imgOld = persistentGremio.getImg();
            AvatarGremio imgNew = gremio.getImg();
            FondoGremio imgFOld = persistentGremio.getImgF();
            FondoGremio imgFNew = gremio.getImgF();
            Set<Raid> listaRaidOld = persistentGremio.getListaRaid();
            Set<Raid> listaRaidNew = gremio.getListaRaid();
            Set<Usuarios> listaUsuariosOld = persistentGremio.getListaUsuarios();
            Set<Usuarios> listaUsuariosNew = gremio.getListaUsuarios();
            Set<UsuarioRoles> listaUsuariosRolesOld = persistentGremio.getListaUsuariosRoles();
            Set<UsuarioRoles> listaUsuariosRolesNew = gremio.getListaUsuariosRoles();
            List<String> illegalOrphanMessages = null;
            for (UsuarioRoles listaUsuariosRolesOldUsuarioRoles : listaUsuariosRolesOld) {
                if (!listaUsuariosRolesNew.contains(listaUsuariosRolesOldUsuarioRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioRoles " + listaUsuariosRolesOldUsuarioRoles + " since its gremiouserrol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (gremioregionNew != null) {
                gremioregionNew = em.getReference(gremioregionNew.getClass(), gremioregionNew.getId());
                gremio.setGremioregion(gremioregionNew);
            }
            if (gremiomundoNew != null) {
                gremiomundoNew = em.getReference(gremiomundoNew.getClass(), gremiomundoNew.getId());
                gremio.setGremiomundo(gremiomundoNew);
            }
            if (imgNew != null) {
                imgNew = em.getReference(imgNew.getClass(), imgNew.getId());
                gremio.setImg(imgNew);
            }
            if (imgFNew != null) {
                imgFNew = em.getReference(imgFNew.getClass(), imgFNew.getId());
                gremio.setImgF(imgFNew);
            }
            Set<Raid> attachedListaRaidNew = new HashSet<Raid>();
            for (Raid listaRaidNewRaidToAttach : listaRaidNew) {
                listaRaidNewRaidToAttach = em.getReference(listaRaidNewRaidToAttach.getClass(), listaRaidNewRaidToAttach.getId());
                attachedListaRaidNew.add(listaRaidNewRaidToAttach);
            }
            listaRaidNew = attachedListaRaidNew;
            gremio.setListaRaid(listaRaidNew);
            Set<Usuarios> attachedListaUsuariosNew = new HashSet<Usuarios>();
            for (Usuarios listaUsuariosNewUsuariosToAttach : listaUsuariosNew) {
                listaUsuariosNewUsuariosToAttach = em.getReference(listaUsuariosNewUsuariosToAttach.getClass(), listaUsuariosNewUsuariosToAttach.getId());
                attachedListaUsuariosNew.add(listaUsuariosNewUsuariosToAttach);
            }
            listaUsuariosNew = attachedListaUsuariosNew;
            gremio.setListaUsuarios(listaUsuariosNew);
            Set<UsuarioRoles> attachedListaUsuariosRolesNew = new HashSet<UsuarioRoles>();
            for (UsuarioRoles listaUsuariosRolesNewUsuarioRolesToAttach : listaUsuariosRolesNew) {
                listaUsuariosRolesNewUsuarioRolesToAttach = em.getReference(listaUsuariosRolesNewUsuarioRolesToAttach.getClass(), listaUsuariosRolesNewUsuarioRolesToAttach.getId());
                attachedListaUsuariosRolesNew.add(listaUsuariosRolesNewUsuarioRolesToAttach);
            }
            listaUsuariosRolesNew = attachedListaUsuariosRolesNew;
            gremio.setListaUsuariosRoles(listaUsuariosRolesNew);
            gremio = em.merge(gremio);
            if (gremioregionOld != null && !gremioregionOld.equals(gremioregionNew)) {
                gremioregionOld.getListaGremios().remove(gremio);
                gremioregionOld = em.merge(gremioregionOld);
            }
            if (gremioregionNew != null && !gremioregionNew.equals(gremioregionOld)) {
                gremioregionNew.getListaGremios().add(gremio);
                gremioregionNew = em.merge(gremioregionNew);
            }
            if (gremiomundoOld != null && !gremiomundoOld.equals(gremiomundoNew)) {
                gremiomundoOld.getListaGremios().remove(gremio);
                gremiomundoOld = em.merge(gremiomundoOld);
            }
            if (gremiomundoNew != null && !gremiomundoNew.equals(gremiomundoOld)) {
                gremiomundoNew.getListaGremios().add(gremio);
                gremiomundoNew = em.merge(gremiomundoNew);
            }
            if (imgOld != null && !imgOld.equals(imgNew)) {
                imgOld.getListaGremios().remove(gremio);
                imgOld = em.merge(imgOld);
            }
            if (imgNew != null && !imgNew.equals(imgOld)) {
                imgNew.getListaGremios().add(gremio);
                imgNew = em.merge(imgNew);
            }
            if (imgFOld != null && !imgFOld.equals(imgFNew)) {
                imgFOld.getListaGremios().remove(gremio);
                imgFOld = em.merge(imgFOld);
            }
            if (imgFNew != null && !imgFNew.equals(imgFOld)) {
                imgFNew.getListaGremios().add(gremio);
                imgFNew = em.merge(imgFNew);
            }
            for (Raid listaRaidOldRaid : listaRaidOld) {
                if (!listaRaidNew.contains(listaRaidOldRaid)) {
                    listaRaidOldRaid.setRaidgremio(null);
                    listaRaidOldRaid = em.merge(listaRaidOldRaid);
                }
            }
            for (Raid listaRaidNewRaid : listaRaidNew) {
                if (!listaRaidOld.contains(listaRaidNewRaid)) {
                    Gremio oldRaidgremioOfListaRaidNewRaid = listaRaidNewRaid.getRaidgremio();
                    listaRaidNewRaid.setRaidgremio(gremio);
                    listaRaidNewRaid = em.merge(listaRaidNewRaid);
                    if (oldRaidgremioOfListaRaidNewRaid != null && !oldRaidgremioOfListaRaidNewRaid.equals(gremio)) {
                        oldRaidgremioOfListaRaidNewRaid.getListaRaid().remove(listaRaidNewRaid);
                        oldRaidgremioOfListaRaidNewRaid = em.merge(oldRaidgremioOfListaRaidNewRaid);
                    }
                }
            }
            for (Usuarios listaUsuariosOldUsuarios : listaUsuariosOld) {
                if (!listaUsuariosNew.contains(listaUsuariosOldUsuarios)) {
                    listaUsuariosOldUsuarios.setGremiousuario(null);
                    listaUsuariosOldUsuarios = em.merge(listaUsuariosOldUsuarios);
                }
            }
            for (Usuarios listaUsuariosNewUsuarios : listaUsuariosNew) {
                if (!listaUsuariosOld.contains(listaUsuariosNewUsuarios)) {
                    Gremio oldGremiousuarioOfListaUsuariosNewUsuarios = listaUsuariosNewUsuarios.getGremiousuario();
                    listaUsuariosNewUsuarios.setGremiousuario(gremio);
                    listaUsuariosNewUsuarios = em.merge(listaUsuariosNewUsuarios);
                    if (oldGremiousuarioOfListaUsuariosNewUsuarios != null && !oldGremiousuarioOfListaUsuariosNewUsuarios.equals(gremio)) {
                        oldGremiousuarioOfListaUsuariosNewUsuarios.getListaUsuarios().remove(listaUsuariosNewUsuarios);
                        oldGremiousuarioOfListaUsuariosNewUsuarios = em.merge(oldGremiousuarioOfListaUsuariosNewUsuarios);
                    }
                }
            }
            for (UsuarioRoles listaUsuariosRolesNewUsuarioRoles : listaUsuariosRolesNew) {
                if (!listaUsuariosRolesOld.contains(listaUsuariosRolesNewUsuarioRoles)) {
                    Gremio oldGremiouserrolOfListaUsuariosRolesNewUsuarioRoles = listaUsuariosRolesNewUsuarioRoles.getGremiouserrol();
                    listaUsuariosRolesNewUsuarioRoles.setGremiouserrol(gremio);
                    listaUsuariosRolesNewUsuarioRoles = em.merge(listaUsuariosRolesNewUsuarioRoles);
                    if (oldGremiouserrolOfListaUsuariosRolesNewUsuarioRoles != null && !oldGremiouserrolOfListaUsuariosRolesNewUsuarioRoles.equals(gremio)) {
                        oldGremiouserrolOfListaUsuariosRolesNewUsuarioRoles.getListaUsuariosRoles().remove(listaUsuariosRolesNewUsuarioRoles);
                        oldGremiouserrolOfListaUsuariosRolesNewUsuarioRoles = em.merge(oldGremiouserrolOfListaUsuariosRolesNewUsuarioRoles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gremio.getId();
                if (findGremio(id) == null) {
                    throw new NonexistentEntityException("The gremio with id " + id + " no longer exists.");
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
            Gremio gremio;
            try {
                gremio = em.getReference(Gremio.class, id);
                gremio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gremio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<UsuarioRoles> listaUsuariosRolesOrphanCheck = gremio.getListaUsuariosRoles();
            for (UsuarioRoles listaUsuariosRolesOrphanCheckUsuarioRoles : listaUsuariosRolesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Gremio (" + gremio + ") cannot be destroyed since the UsuarioRoles " + listaUsuariosRolesOrphanCheckUsuarioRoles + " in its listaUsuariosRoles field has a non-nullable gremiouserrol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Region gremioregion = gremio.getGremioregion();
            if (gremioregion != null) {
                gremioregion.getListaGremios().remove(gremio);
                gremioregion = em.merge(gremioregion);
            }
            Mundos gremiomundo = gremio.getGremiomundo();
            if (gremiomundo != null) {
                gremiomundo.getListaGremios().remove(gremio);
                gremiomundo = em.merge(gremiomundo);
            }
            AvatarGremio img = gremio.getImg();
            if (img != null) {
                img.getListaGremios().remove(gremio);
                img = em.merge(img);
            }
            FondoGremio imgF = gremio.getImgF();
            if (imgF != null) {
                imgF.getListaGremios().remove(gremio);
                imgF = em.merge(imgF);
            }
            Set<Raid> listaRaid = gremio.getListaRaid();
            for (Raid listaRaidRaid : listaRaid) {
                listaRaidRaid.setRaidgremio(null);
                listaRaidRaid = em.merge(listaRaidRaid);
            }
            Set<Usuarios> listaUsuarios = gremio.getListaUsuarios();
            for (Usuarios listaUsuariosUsuarios : listaUsuarios) {
                listaUsuariosUsuarios.setGremiousuario(null);
                listaUsuariosUsuarios = em.merge(listaUsuariosUsuarios);
            }
            em.remove(gremio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gremio> findGremioEntities() {
        return findGremioEntities(true, -1, -1);
    }

    public List<Gremio> findGremioEntities(int maxResults, int firstResult) {
        return findGremioEntities(false, maxResults, firstResult);
    }

    private List<Gremio> findGremioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gremio.class));
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

    public Gremio findGremio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gremio.class, id);
        } finally {
            em.close();
        }
    }

    public int getGremioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gremio> rt = cq.from(Gremio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
