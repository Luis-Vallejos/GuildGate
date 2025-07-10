package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.Participaciones;
import com.guildgate.web.Persistence.ParticipacionesJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IParticipacionesService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class ParticipacionesServiceImpl implements IParticipacionesService {

    private static final Logger LOGGER = Logger.getLogger(ParticipacionesServiceImpl.class.getName());

    @Inject
    ParticipacionesJpaController pjc;

    public ParticipacionesServiceImpl() {
        this.pjc = new ParticipacionesJpaController();
    }

    @Override
    public Participaciones findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return pjc.findParticipaciones(id);
    }

    @Override
    public ArrayList<Participaciones> findAll() {
        return SvUtils.toArrayList(pjc.findParticipacionesEntities());
    }

    @Override
    public boolean create(Participaciones entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Participaciones es null");
            return false;
        }
        try {
            pjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Participaciones: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Participaciones entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            pjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar Participaciones: {0}", ex.toString());
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
            pjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Participaciones con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Participaciones: {0}", ex.toString());
            return false;
        }
    }

}
