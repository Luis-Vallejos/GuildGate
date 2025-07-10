package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.ParticipacionesExtra;
import com.guildgate.web.Persistence.ParticipacionesExtraJpaController;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IParticipacionesExtraService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class ParticipacionesExtraServiceImpl implements IParticipacionesExtraService {

    private static final Logger LOGGER = Logger.getLogger(ParticipacionesExtraServiceImpl.class.getName());

    @Inject
    ParticipacionesExtraJpaController pejc;

    public ParticipacionesExtraServiceImpl() {
        this.pejc = new ParticipacionesExtraJpaController();
    }

    @Override
    public ParticipacionesExtra findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return pejc.findParticipacionesExtra(id);
    }

    @Override
    public ArrayList<ParticipacionesExtra> findAll() {
        return SvUtils.toArrayList(pejc.findParticipacionesExtraEntities());
    }

    @Override
    public boolean create(ParticipacionesExtra entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: ParticipacionesExtra es null");
            return false;
        }
        try {
            pejc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear ParticipacionesExtra: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(ParticipacionesExtra entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            pejc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar ParticipacionesExtra: {0}", ex.toString());
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
            pejc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Bosses con ID {0}", id);
            return false;
        }
    }

}
