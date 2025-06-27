package com.guildgate.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Participaciones;
import java.util.ArrayList;
import java.util.List;
import com.guildgate.web.Modelo.Ronda;
import com.guildgate.web.Modelo.Bosses;
import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Juan - Luis
 */
public class RaidJpaController extends AbstractJpaController implements Serializable {

    public void create(Raid raid) {
        if (raid.getListaParticipaciones() == null) {
            raid.setListaParticipaciones(new ArrayList<Participaciones>());
        }
        if (raid.getListaRondas() == null) {
            raid.setListaRondas(new ArrayList<Ronda>());
        }
        if (raid.getListaBosses() == null) {
            raid.setListaBosses(new ArrayList<Bosses>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gremio raidgremio = raid.getRaidgremio();
            if (raidgremio != null) {
                raidgremio = em.getReference(raidgremio.getClass(), raidgremio.getId());
                raid.setRaidgremio(raidgremio);
            }
            List<Participaciones> attachedListaParticipaciones = new ArrayList<Participaciones>();
            for (Participaciones listaParticipacionesParticipacionesToAttach : raid.getListaParticipaciones()) {
                listaParticipacionesParticipacionesToAttach = em.getReference(listaParticipacionesParticipacionesToAttach.getClass(), listaParticipacionesParticipacionesToAttach.getId());
                attachedListaParticipaciones.add(listaParticipacionesParticipacionesToAttach);
            }
            raid.setListaParticipaciones(attachedListaParticipaciones);
            List<Ronda> attachedListaRondas = new ArrayList<Ronda>();
            for (Ronda listaRondasRondaToAttach : raid.getListaRondas()) {
                listaRondasRondaToAttach = em.getReference(listaRondasRondaToAttach.getClass(), listaRondasRondaToAttach.getId());
                attachedListaRondas.add(listaRondasRondaToAttach);
            }
            raid.setListaRondas(attachedListaRondas);
            List<Bosses> attachedListaBosses = new ArrayList<Bosses>();
            for (Bosses listaBossesBossesToAttach : raid.getListaBosses()) {
                listaBossesBossesToAttach = em.getReference(listaBossesBossesToAttach.getClass(), listaBossesBossesToAttach.getId());
                attachedListaBosses.add(listaBossesBossesToAttach);
            }
            raid.setListaBosses(attachedListaBosses);
            em.persist(raid);
            if (raidgremio != null) {
                raidgremio.getListaRaid().add(raid);
                raidgremio = em.merge(raidgremio);
            }
            for (Participaciones listaParticipacionesParticipaciones : raid.getListaParticipaciones()) {
                Raid oldRaidpartiOfListaParticipacionesParticipaciones = listaParticipacionesParticipaciones.getRaidparti();
                listaParticipacionesParticipaciones.setRaidparti(raid);
                listaParticipacionesParticipaciones = em.merge(listaParticipacionesParticipaciones);
                if (oldRaidpartiOfListaParticipacionesParticipaciones != null) {
                    oldRaidpartiOfListaParticipacionesParticipaciones.getListaParticipaciones().remove(listaParticipacionesParticipaciones);
                    oldRaidpartiOfListaParticipacionesParticipaciones = em.merge(oldRaidpartiOfListaParticipacionesParticipaciones);
                }
            }
            for (Ronda listaRondasRonda : raid.getListaRondas()) {
                Raid oldRaidrondaOfListaRondasRonda = listaRondasRonda.getRaidronda();
                listaRondasRonda.setRaidronda(raid);
                listaRondasRonda = em.merge(listaRondasRonda);
                if (oldRaidrondaOfListaRondasRonda != null) {
                    oldRaidrondaOfListaRondasRonda.getListaRondas().remove(listaRondasRonda);
                    oldRaidrondaOfListaRondasRonda = em.merge(oldRaidrondaOfListaRondasRonda);
                }
            }
            for (Bosses listaBossesBosses : raid.getListaBosses()) {
                Raid oldRaidbossOfListaBossesBosses = listaBossesBosses.getRaidboss();
                listaBossesBosses.setRaidboss(raid);
                listaBossesBosses = em.merge(listaBossesBosses);
                if (oldRaidbossOfListaBossesBosses != null) {
                    oldRaidbossOfListaBossesBosses.getListaBosses().remove(listaBossesBosses);
                    oldRaidbossOfListaBossesBosses = em.merge(oldRaidbossOfListaBossesBosses);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Raid raid) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Raid persistentRaid = em.find(Raid.class, raid.getId());
            Gremio raidgremioOld = persistentRaid.getRaidgremio();
            Gremio raidgremioNew = raid.getRaidgremio();
            List<Participaciones> listaParticipacionesOld = persistentRaid.getListaParticipaciones();
            List<Participaciones> listaParticipacionesNew = raid.getListaParticipaciones();
            List<Ronda> listaRondasOld = persistentRaid.getListaRondas();
            List<Ronda> listaRondasNew = raid.getListaRondas();
            List<Bosses> listaBossesOld = persistentRaid.getListaBosses();
            List<Bosses> listaBossesNew = raid.getListaBosses();
            List<String> illegalOrphanMessages = null;
            for (Participaciones listaParticipacionesOldParticipaciones : listaParticipacionesOld) {
                if (!listaParticipacionesNew.contains(listaParticipacionesOldParticipaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participaciones " + listaParticipacionesOldParticipaciones + " since its raidparti field is not nullable.");
                }
            }
            for (Ronda listaRondasOldRonda : listaRondasOld) {
                if (!listaRondasNew.contains(listaRondasOldRonda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ronda " + listaRondasOldRonda + " since its raidronda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (raidgremioNew != null) {
                raidgremioNew = em.getReference(raidgremioNew.getClass(), raidgremioNew.getId());
                raid.setRaidgremio(raidgremioNew);
            }
            List<Participaciones> attachedListaParticipacionesNew = new ArrayList<Participaciones>();
            for (Participaciones listaParticipacionesNewParticipacionesToAttach : listaParticipacionesNew) {
                listaParticipacionesNewParticipacionesToAttach = em.getReference(listaParticipacionesNewParticipacionesToAttach.getClass(), listaParticipacionesNewParticipacionesToAttach.getId());
                attachedListaParticipacionesNew.add(listaParticipacionesNewParticipacionesToAttach);
            }
            listaParticipacionesNew = attachedListaParticipacionesNew;
            raid.setListaParticipaciones(listaParticipacionesNew);
            List<Ronda> attachedListaRondasNew = new ArrayList<Ronda>();
            for (Ronda listaRondasNewRondaToAttach : listaRondasNew) {
                listaRondasNewRondaToAttach = em.getReference(listaRondasNewRondaToAttach.getClass(), listaRondasNewRondaToAttach.getId());
                attachedListaRondasNew.add(listaRondasNewRondaToAttach);
            }
            listaRondasNew = attachedListaRondasNew;
            raid.setListaRondas(listaRondasNew);
            List<Bosses> attachedListaBossesNew = new ArrayList<Bosses>();
            for (Bosses listaBossesNewBossesToAttach : listaBossesNew) {
                listaBossesNewBossesToAttach = em.getReference(listaBossesNewBossesToAttach.getClass(), listaBossesNewBossesToAttach.getId());
                attachedListaBossesNew.add(listaBossesNewBossesToAttach);
            }
            listaBossesNew = attachedListaBossesNew;
            raid.setListaBosses(listaBossesNew);
            raid = em.merge(raid);
            if (raidgremioOld != null && !raidgremioOld.equals(raidgremioNew)) {
                raidgremioOld.getListaRaid().remove(raid);
                raidgremioOld = em.merge(raidgremioOld);
            }
            if (raidgremioNew != null && !raidgremioNew.equals(raidgremioOld)) {
                raidgremioNew.getListaRaid().add(raid);
                raidgremioNew = em.merge(raidgremioNew);
            }
            for (Participaciones listaParticipacionesNewParticipaciones : listaParticipacionesNew) {
                if (!listaParticipacionesOld.contains(listaParticipacionesNewParticipaciones)) {
                    Raid oldRaidpartiOfListaParticipacionesNewParticipaciones = listaParticipacionesNewParticipaciones.getRaidparti();
                    listaParticipacionesNewParticipaciones.setRaidparti(raid);
                    listaParticipacionesNewParticipaciones = em.merge(listaParticipacionesNewParticipaciones);
                    if (oldRaidpartiOfListaParticipacionesNewParticipaciones != null && !oldRaidpartiOfListaParticipacionesNewParticipaciones.equals(raid)) {
                        oldRaidpartiOfListaParticipacionesNewParticipaciones.getListaParticipaciones().remove(listaParticipacionesNewParticipaciones);
                        oldRaidpartiOfListaParticipacionesNewParticipaciones = em.merge(oldRaidpartiOfListaParticipacionesNewParticipaciones);
                    }
                }
            }
            for (Ronda listaRondasNewRonda : listaRondasNew) {
                if (!listaRondasOld.contains(listaRondasNewRonda)) {
                    Raid oldRaidrondaOfListaRondasNewRonda = listaRondasNewRonda.getRaidronda();
                    listaRondasNewRonda.setRaidronda(raid);
                    listaRondasNewRonda = em.merge(listaRondasNewRonda);
                    if (oldRaidrondaOfListaRondasNewRonda != null && !oldRaidrondaOfListaRondasNewRonda.equals(raid)) {
                        oldRaidrondaOfListaRondasNewRonda.getListaRondas().remove(listaRondasNewRonda);
                        oldRaidrondaOfListaRondasNewRonda = em.merge(oldRaidrondaOfListaRondasNewRonda);
                    }
                }
            }
            for (Bosses listaBossesOldBosses : listaBossesOld) {
                if (!listaBossesNew.contains(listaBossesOldBosses)) {
                    listaBossesOldBosses.setRaidboss(null);
                    listaBossesOldBosses = em.merge(listaBossesOldBosses);
                }
            }
            for (Bosses listaBossesNewBosses : listaBossesNew) {
                if (!listaBossesOld.contains(listaBossesNewBosses)) {
                    Raid oldRaidbossOfListaBossesNewBosses = listaBossesNewBosses.getRaidboss();
                    listaBossesNewBosses.setRaidboss(raid);
                    listaBossesNewBosses = em.merge(listaBossesNewBosses);
                    if (oldRaidbossOfListaBossesNewBosses != null && !oldRaidbossOfListaBossesNewBosses.equals(raid)) {
                        oldRaidbossOfListaBossesNewBosses.getListaBosses().remove(listaBossesNewBosses);
                        oldRaidbossOfListaBossesNewBosses = em.merge(oldRaidbossOfListaBossesNewBosses);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = raid.getId();
                if (findRaid(id) == null) {
                    throw new NonexistentEntityException("The raid with id " + id + " no longer exists.");
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
            Raid raid;
            try {
                raid = em.getReference(Raid.class, id);
                raid.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The raid with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Participaciones> listaParticipacionesOrphanCheck = raid.getListaParticipaciones();
            for (Participaciones listaParticipacionesOrphanCheckParticipaciones : listaParticipacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Raid (" + raid + ") cannot be destroyed since the Participaciones " + listaParticipacionesOrphanCheckParticipaciones + " in its listaParticipaciones field has a non-nullable raidparti field.");
            }
            List<Ronda> listaRondasOrphanCheck = raid.getListaRondas();
            for (Ronda listaRondasOrphanCheckRonda : listaRondasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Raid (" + raid + ") cannot be destroyed since the Ronda " + listaRondasOrphanCheckRonda + " in its listaRondas field has a non-nullable raidronda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Gremio raidgremio = raid.getRaidgremio();
            if (raidgremio != null) {
                raidgremio.getListaRaid().remove(raid);
                raidgremio = em.merge(raidgremio);
            }
            List<Bosses> listaBosses = raid.getListaBosses();
            for (Bosses listaBossesBosses : listaBosses) {
                listaBossesBosses.setRaidboss(null);
                listaBossesBosses = em.merge(listaBossesBosses);
            }
            em.remove(raid);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Raid> findRaidEntities() {
        return findRaidEntities(true, -1, -1);
    }

    public List<Raid> findRaidEntities(int maxResults, int firstResult) {
        return findRaidEntities(false, maxResults, firstResult);
    }

    private List<Raid> findRaidEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Raid.class));
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

    public Raid findRaid(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Raid.class, id);
        } finally {
            em.close();
        }
    }

    public int getRaidCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Raid> rt = cq.from(Raid.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
