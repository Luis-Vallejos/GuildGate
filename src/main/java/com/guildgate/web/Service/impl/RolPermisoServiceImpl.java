package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.RolPermiso;
import com.guildgate.web.Modelo.RolPermisoId;
import com.guildgate.web.Persistence.RolPermisoJpaController;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IRolPermisoService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class RolPermisoServiceImpl implements IRolPermisoService {

    private static final Logger LOGGER = Logger.getLogger(RolPermisoServiceImpl.class.getName());

    @Inject
    RolPermisoJpaController rpjc;

    public RolPermisoServiceImpl() {
        this.rpjc = new RolPermisoJpaController();
    }

    @Override
    public RolPermiso findById(RolPermisoId id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return rpjc.findRolPermiso(id);
    }

    @Override
    public ArrayList<RolPermiso> findAll() {
        return SvUtils.toArrayList(rpjc.findRolPermisoEntities());
    }

    @Override
    public boolean create(RolPermiso entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: RolPermiso es null");
            return false;
        }
        try {
            rpjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear RolPermiso: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(RolPermiso entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            rpjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar RolPermiso: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean delete(RolPermisoId id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "delete falló: id es null");
            return false;
        }
        try {
            rpjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe RolPermiso con ID {0}", id);
            return false;
        }
    }

}
