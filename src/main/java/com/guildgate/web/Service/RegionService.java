package com.guildgate.web.Service;

import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Persistence.RegionJpaController;
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
public class RegionService implements IRegionService {

    private static final Logger LOGGER = Logger.getLogger(RegionService.class.getName());

    @Inject
    RegionJpaController rjc;

    public RegionService() {
        this.rjc = new RegionJpaController();
    }

    @Override
    public Region findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return rjc.findRegion(id);
    }

    @Override
    public ArrayList<Region> findAll() {
        return SvUtils.toArrayList(rjc.findRegionEntities());
    }

    @Override
    public boolean create(Region entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Region es null");
            return false;
        }
        try {
            rjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Region: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Region entity) {
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
            LOGGER.log(Level.SEVERE, "Error al editar Region: {0}", ex.toString());
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
            LOGGER.log(Level.WARNING, "delete falló: no existe Region con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Region: {0}", ex.toString());
            return false;
        }
    }

}
