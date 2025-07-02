package com.guildgate.web.Service;

import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Persistence.FondoGremioJpaController;
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
public class FondoGremioService implements IFondoGremioService {

    private static final Logger LOGGER = Logger.getLogger(FondoGremio.class.getName());

    @Inject
    FondoGremioJpaController fgjc;

    public FondoGremioService() {
        this.fgjc = new FondoGremioJpaController();
    }

    @Override
    public FondoGremio findById(Long id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return fgjc.findFondoGremio(id);
    }

    @Override
    public ArrayList<FondoGremio> findAll() {
        ArrayList<FondoGremio> fondogremios = SvUtils.toArrayList(fgjc.findFondoGremioEntities());
        return fondogremios;
    }

    @Override
    public boolean create(FondoGremio entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create fallo: FondoGremio es null");
            return false;
        }
        try {
            fgjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear FondoGremio: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(FondoGremio entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }

        try {
            fgjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar FondoGremio: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "delete falló: id es null");
            return false;
        }
        try {
            fgjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe FondoGremio con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar FondoGremio: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public FondoGremio buscarFondoGremioPorNombre(String nombreArchivo) {
        return fgjc.findImagenByNombre(nombreArchivo);
    }
}
