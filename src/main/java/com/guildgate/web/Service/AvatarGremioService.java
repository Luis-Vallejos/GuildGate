package com.guildgate.web.Service;

import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Persistence.AvatarGremioJpaController;
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
public class AvatarGremioService implements IAvatarGremioService {

    private static final Logger LOGGER = Logger.getLogger(AvatarGremioService.class.getName());

    @Inject
    AvatarGremioJpaController agjc;

    public AvatarGremioService() {
        this.agjc = new AvatarGremioJpaController();
    }

    @Override
    public AvatarGremio findById(Long id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return agjc.findAvatarGremio(id);
    }

    @Override
    public ArrayList<AvatarGremio> findAll() {
        return SvUtils.toArrayList(agjc.findAvatarGremioEntities());
    }

    @Override
    public boolean create(AvatarGremio entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: AvatarGremio es null");
            return false;
        }
        try {
            agjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear AvatarGremio: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(AvatarGremio entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            agjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar AvatarGremio: {0}", ex.toString());
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
            agjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe AvatarGremio con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar AvatarGremio: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public AvatarGremio buscarAvatarGremioPorNombre(String nombreArchivo) {
        return agjc.findImagenByNombre(nombreArchivo);
    }
}
