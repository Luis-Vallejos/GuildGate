package com.guildgate.web.Service;

import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Persistence.ImagenPerfilJpaController;
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
public class PerfilService implements IPerfilService {

    private static final Logger LOGGER = Logger.getLogger(PerfilService.class.getName());

    @Inject
    ImagenPerfilJpaController ijc;

    public PerfilService() {
        this.ijc = new ImagenPerfilJpaController();
    }

    @Override
    public ImagenPerfil findById(Long id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return ijc.findImagenPerfil(id);
    }

    @Override
    public ArrayList<ImagenPerfil> findAll() {
        return SvUtils.toArrayList(ijc.findImagenPerfilEntities());
    }

    @Override
    public boolean create(ImagenPerfil entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: ImagenPerfil es null");
            return false;
        }
        try {
            ijc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear ImagenPerfil: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(ImagenPerfil entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            ijc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar ImagenPerfil: {0}", ex.toString());
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
            ijc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe ImagenPerfil con ID {0}", id);
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar ImagenPerfil: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public ImagenPerfil buscarImagenPorNombre(String nombreArchivo) {
        if (nombreArchivo.isEmpty()) {
            LOGGER.log(Level.WARNING, "buscarImagenPorNombre falló: nombreArchivo es null");
            return null;
        }
        return ijc.findImagenByNombre(nombreArchivo);
    }
}
