package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.Ronda;
import com.guildgate.web.Persistence.RondaJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IRondaService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class RondaServiceImpl implements IRondaService {

    private static final Logger LOGGER = Logger.getLogger(RondaServiceImpl.class.getName());

    @Inject
    RondaJpaController rjc;

    public RondaServiceImpl() {
        this.rjc = new RondaJpaController();
    }

    @Override
    public Ronda findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return rjc.findRonda(id);
    }

    @Override
    public ArrayList<Ronda> findAll() {
        return SvUtils.toArrayList(rjc.findRondaEntities());
    }

    @Override
    public boolean create(Ronda entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Ronda es null");
            return false;
        }
        try {
            rjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Ronda: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Ronda entity) {
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
            LOGGER.log(Level.SEVERE, "Error al editar Ronda: {0}", ex.toString());
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
            LOGGER.log(Level.WARNING, "delete falló: no existe Bosses con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Ronda: {0}", ex.toString());
            return false;
        }
    }

}
