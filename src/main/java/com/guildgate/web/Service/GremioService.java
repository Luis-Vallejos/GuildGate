package com.guildgate.web.Service;

import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Persistence.GremioJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class GremioService implements IGremioService {

    private static final Logger LOGGER = Logger.getLogger(GremioService.class.getName());

    @Inject
    GremioJpaController gpc;

    public GremioService() {
        this.gpc = new GremioJpaController();
    }

    @Override
    public Gremio findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return gpc.findGremio(id);
    }

    @Override
    public ArrayList<Gremio> findAll() {
        return SvUtils.toArrayList(gpc.findGremioEntities());
    }

    @Override
    public boolean create(Gremio entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Gremio es null");
            return false;
        }
        try {
            gpc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Gremio: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Gremio entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            gpc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar Gremio: {0}", ex.toString());
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
            gpc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Gremio con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Gremio: {0}", ex.toString());
            return false;
        }
    }
}
