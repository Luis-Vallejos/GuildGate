package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.Permiso;
import com.guildgate.web.Persistence.PermisoJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IPermisoService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class PermisoServiceImpl implements IPermisoService {

    private static final Logger LOGGER = Logger.getLogger(PermisoServiceImpl.class.getName());

    @Inject
    PermisoJpaController pjc;

    public PermisoServiceImpl() {
        this.pjc = new PermisoJpaController();
    }

    @Override
    public Permiso findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return pjc.findPermiso(id);
    }

    @Override
    public ArrayList<Permiso> findAll() {
        return SvUtils.toArrayList(pjc.findPermisoEntities());
    }

    @Override
    public boolean create(Permiso entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Permiso es null");
            return false;
        }
        try {
            pjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Permiso: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Permiso entity) {
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
            LOGGER.log(Level.SEVERE, "Error al editar Permiso: {0}", ex.toString());
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
            LOGGER.log(Level.WARNING, "delete falló: no existe Bosses con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Bosses: {0}", ex.toString());
            return false;
        }
    }

}
