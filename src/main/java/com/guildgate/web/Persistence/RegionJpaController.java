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
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Region;
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
public class RegionJpaController implements Serializable {

    public RegionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Region region) {
        if (region.getListaGremios() == null) {
            region.setListaGremios(new ArrayList<Gremio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Gremio> attachedListaGremios = new ArrayList<Gremio>();
            for (Gremio listaGremiosGremioToAttach : region.getListaGremios()) {
                listaGremiosGremioToAttach = em.getReference(listaGremiosGremioToAttach.getClass(), listaGremiosGremioToAttach.getId());
                attachedListaGremios.add(listaGremiosGremioToAttach);
            }
            region.setListaGremios(attachedListaGremios);
            em.persist(region);
            for (Gremio listaGremiosGremio : region.getListaGremios()) {
                Region oldGremioregionOfListaGremiosGremio = listaGremiosGremio.getGremioregion();
                listaGremiosGremio.setGremioregion(region);
                listaGremiosGremio = em.merge(listaGremiosGremio);
                if (oldGremioregionOfListaGremiosGremio != null) {
                    oldGremioregionOfListaGremiosGremio.getListaGremios().remove(listaGremiosGremio);
                    oldGremioregionOfListaGremiosGremio = em.merge(oldGremioregionOfListaGremiosGremio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Region region) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region persistentRegion = em.find(Region.class, region.getId());
            List<Gremio> listaGremiosOld = persistentRegion.getListaGremios();
            List<Gremio> listaGremiosNew = region.getListaGremios();
            List<String> illegalOrphanMessages = null;
            for (Gremio listaGremiosOldGremio : listaGremiosOld) {
                if (!listaGremiosNew.contains(listaGremiosOldGremio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gremio " + listaGremiosOldGremio + " since its gremioregion field is not nullable.");
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
            region.setListaGremios(listaGremiosNew);
            region = em.merge(region);
            for (Gremio listaGremiosNewGremio : listaGremiosNew) {
                if (!listaGremiosOld.contains(listaGremiosNewGremio)) {
                    Region oldGremioregionOfListaGremiosNewGremio = listaGremiosNewGremio.getGremioregion();
                    listaGremiosNewGremio.setGremioregion(region);
                    listaGremiosNewGremio = em.merge(listaGremiosNewGremio);
                    if (oldGremioregionOfListaGremiosNewGremio != null && !oldGremioregionOfListaGremiosNewGremio.equals(region)) {
                        oldGremioregionOfListaGremiosNewGremio.getListaGremios().remove(listaGremiosNewGremio);
                        oldGremioregionOfListaGremiosNewGremio = em.merge(oldGremioregionOfListaGremiosNewGremio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = region.getId();
                if (findRegion(id) == null) {
                    throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
            Region region;
            try {
                region = em.getReference(Region.class, id);
                region.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Gremio> listaGremiosOrphanCheck = region.getListaGremios();
            for (Gremio listaGremiosOrphanCheckGremio : listaGremiosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Region (" + region + ") cannot be destroyed since the Gremio " + listaGremiosOrphanCheckGremio + " in its listaGremios field has a non-nullable gremioregion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(region);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Region> findRegionEntities() {
        return findRegionEntities(true, -1, -1);
    }

    public List<Region> findRegionEntities(int maxResults, int firstResult) {
        return findRegionEntities(false, maxResults, firstResult);
    }

    private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Region.class));
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

    public Region findRegion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Region.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Region> rt = cq.from(Region.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
