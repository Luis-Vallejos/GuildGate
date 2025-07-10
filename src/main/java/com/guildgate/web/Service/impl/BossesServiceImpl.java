package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.Bosses;
import com.guildgate.web.Persistence.BossesJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IBossesService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class BossesServiceImpl implements IBossesService {

    private static final Logger LOGGER = Logger.getLogger(BossesServiceImpl.class.getName());

    @Inject
    BossesJpaController bjc;

    public BossesServiceImpl() {
        this.bjc = new BossesJpaController();
    }

    @Override
    public Bosses findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return bjc.findBosses(id);
    }

    @Override
    public ArrayList<Bosses> findAll() {
        return SvUtils.toArrayList(bjc.findBossesEntities());
    }

    @Override
    public boolean create(Bosses entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Bosses es null");
            return false;
        }
        try {
            bjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Bosses: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Bosses entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            bjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar Bosses: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "delete falló: id es null");
            return false;
        }
        try {
            bjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Bosses con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Bosses: {0}", ex.toString());
            return false;
        }
    }

}
