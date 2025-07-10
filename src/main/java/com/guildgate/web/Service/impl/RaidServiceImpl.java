package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Persistence.RaidJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IRaidService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class RaidServiceImpl implements IRaidService {

    private static final Logger LOGGER = Logger.getLogger(RaidServiceImpl.class.getName());

    @Inject
    RaidJpaController rjc;

    public RaidServiceImpl() {
        this.rjc = new RaidJpaController();
    }

    @Override
    public Raid findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return rjc.findRaid(id);
    }

    @Override
    public ArrayList<Raid> findAll() {
        return SvUtils.toArrayList(rjc.findRaidEntities());
    }

    @Override
    public boolean create(Raid entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Raid es null");
            return false;
        }
        try {
            rjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Raid: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Raid entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            rjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar Raid: {0}", ex.toString());
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
            rjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Raid con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Raid: {0}", ex.toString());
            return false;
        }
    }

}
